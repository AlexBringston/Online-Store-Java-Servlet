package com.store.model.dao.impl;

import com.store.model.dao.DeletedProductDao;
import com.store.model.dao.mapper.ProductMapper;
import com.store.model.entity.Product;
import com.store.model.exception.DatabaseException;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("ALL")
public class JDBCDeletedProductDao implements DeletedProductDao {

    private Connection connection;

    private static final Logger log = Logger.getLogger(JDBCDeletedProductDao.class);

    public JDBCDeletedProductDao(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void create(Product entity) throws DatabaseException {
        ResultSet resultSet = null;
        PreparedStatement preparedStatement = null;
        try {
            connection.setAutoCommit(false);
            preparedStatement = connection.prepareStatement("INSERT INTO deleted_products (name, name_uk, " +
                            "image_link, " +
                            "price, category_id, size_id, color_id) VALUES (?,?,?,?," +
                            "(SELECT id FROM categories WHERE name = ?)," +
                            "(SELECT id FROM sizes WHERE name = ?)," +
                            "(SELECT id FROM colors WHERE name = ?) )",
                    Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, entity.getName());
            preparedStatement.setString(2, entity.getNameUK());
            preparedStatement.setString(3, entity.getImageLink());
            preparedStatement.setBigDecimal(4, entity.getPrice());
            preparedStatement.setString(5, entity.getCategory());
            preparedStatement.setString(6, entity.getSize());
            preparedStatement.setString(7, entity.getColor());
            preparedStatement.executeUpdate();
            resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet.next()) {
                entity.setId(resultSet.getInt("id"));
                entity.setCreatedAt(resultSet.getTimestamp("created_at"));
            }
            connection.commit();
            preparedStatement.close();
        } catch (SQLException ex) {
            try {
                connection.rollback();
            } catch (SQLException throwables) {
                log.trace("Error with trying to rollback connection");
            }
            throw new DatabaseException("Could not add a deleted product", ex);
        } finally {
            close();
        }
    }

    @Override
    public Product findById(int id, String locale) throws DatabaseException {
        Product product = new Product();
        Statement statement = null;
        ResultSet rs = null;
        try {
            ProductMapper mapper = new ProductMapper();
            statement = connection.createStatement();
            String SQL = "SELECT p.*, c.name as category, co.name as color, s" +
                    ".name as size from deleted_products p, categories c, colors co, sizes s WHERE p.id = %d and c.id" +
                    " = p" +
                    ".category_id and " +
                    "co.id = p.color_id and s.id = p.size_id";
            rs = statement.executeQuery(String.format(SQL, id));
            if (rs.next()) {
                product = mapper.extractFromResultSet(rs, "");
            }
            rs.close();
            statement.close();
        } catch (SQLException ex) {
            log.trace("Could not find deleted product");
            throw new DatabaseException("Error with trying to find a deleted product", ex);
        } finally {
            close();
        }
        return product;
    }

    @Override
    public List<Product> findAll() throws DatabaseException {
        List<Product> productList = new ArrayList<>();
        Statement statement = null;
        ResultSet rs = null;
        try {
            ProductMapper mapper = new ProductMapper();
            statement = connection.createStatement();
            rs = statement.executeQuery("SELECT * FROM deleted_products");
            while (rs.next()) {
                productList.add(mapper.extractFromResultSet(rs, ""));
            }
            rs.close();
            statement.close();
        } catch (SQLException ex) {
            log.trace("Could not list deleted products");
            throw new DatabaseException("Error with trying to find all deleted products", ex);
        } finally {
            close();
        }
        return productList;
    }

    @Override
    public void update(Product entity) throws DatabaseException {
        PreparedStatement preparedStatement = null;
        try {
            connection.setAutoCommit(false);
            preparedStatement = connection.prepareStatement("UPDATE deleted_products SET name = ?," +
                    "image_link = ?, price = ?, category_id = (SELECT id FROM categories WHERE name = ?)," +
                    " size_id = (SELECT id FROM sizes WHERE name = ?), color_id = (SELECT id FROM colors WHERE name =" +
                    " ?)");
            preparedStatement.setString(1, entity.getName());
            preparedStatement.setString(2, entity.getImageLink());
            preparedStatement.setBigDecimal(3, entity.getPrice());
            preparedStatement.setString(4, entity.getCategory());
            preparedStatement.setString(5, entity.getSize());
            preparedStatement.setString(6, entity.getColor());
            preparedStatement.executeUpdate();
            connection.commit();
            preparedStatement.close();
        } catch (SQLException ex) {
            try {
                connection.rollback();
            } catch (SQLException throwables) {
                log.trace("Could not update deleted product");
            }
            throw new DatabaseException("Error with trying to update a deleted product info", ex);
        } finally {
            close();
        }
    }

    @Override
    public void delete(int id) throws DatabaseException {
        PreparedStatement preparedStatement = null;
        try {
            connection.setAutoCommit(false);
            preparedStatement = connection.prepareStatement("DELETE FROM deleted_products WHERE id = ?");
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();

            connection.commit();
            preparedStatement.close();
        } catch (SQLException ex) {
            try {
                connection.rollback();
            } catch (SQLException throwables) {
                log.trace("Could not remove deleted product");
            }
            throw new DatabaseException("Error with trying to remove a deleted product", ex);
        } finally {
            close();
        }
    }

    @Override
    public void close() {
        try {
            connection.close();
        } catch (SQLException throwables) {
            log.trace("Could not close a connection");
        }
    }
}
