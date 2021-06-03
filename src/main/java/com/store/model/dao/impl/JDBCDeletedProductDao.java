package com.store.model.dao.impl;

import com.store.model.dao.DeletedProductDao;
import com.store.model.dao.mapper.ProductMapper;
import com.store.model.entity.Product;
import com.store.model.service.ProductService;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("ALL")
public class JDBCDeletedProductDao implements DeletedProductDao {

    private Connection connection;

    private static final Logger log = Logger.getLogger(ProductService.class);

    public JDBCDeletedProductDao(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void create(Product entity) {
        ResultSet resultSet = null;
        PreparedStatement preparedStatement = null;
        try {
            connection.setAutoCommit(false);
            preparedStatement = connection.prepareStatement("INSERT INTO deleted_products (name, description, " +
                            "image_link, " +
                            "price, category_id, size_id, color_id) VALUES (?,?,?,?," +
                            "(SELECT id FROM categories WHERE name = ?)," +
                            "(SELECT id FROM sizes WHERE name = ?)," +
                            "(SELECT id FROM colors WHERE name = ?) )",
                    Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, entity.getName());
            preparedStatement.setString(2, entity.getDescription());
            preparedStatement.setString(3, entity.getImageLink());
            preparedStatement.setInt(4, entity.getPrice());
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
                throwables.printStackTrace();
            }
            ex.printStackTrace();
        } finally {
            close();
        }
    }

    @Override
    public Product findById(int id) {
        Product product = new Product();
        Statement statement = null;
        ResultSet rs = null;
        try {
            connection.setAutoCommit(false);
            ProductMapper mapper = new ProductMapper();
            statement = connection.createStatement();
            String SQL = "SELECT p.*, c.name as category, co.name as color, s" +
                    ".name as size from deleted_products p, categories c, colors co, sizes s WHERE p.id = %d and c.id" +
                    " = p" +
                    ".category_id and " +
                    "co.id = p.color_id and s.id = p.size_id";
            rs = statement.executeQuery(String.format(SQL, id));
            if (rs.next()) {
                product = mapper.extractFromResultSet(rs);
            }
            rs.close();
            statement.close();
        } catch (SQLException ex) {
            try {
                connection.rollback();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            ex.printStackTrace();
        } finally {
            close();
        }
        return product;
    }

    @Override
    public List<Product> findAll() {
        List<Product> productList = new ArrayList<>();
        Statement statement = null;
        ResultSet rs = null;
        try {
            connection.setAutoCommit(false);
            ProductMapper mapper = new ProductMapper();
            statement = connection.createStatement();
            rs = statement.executeQuery("SELECT * FROM deleted_products");
            while (rs.next()) {
                productList.add(mapper.extractFromResultSet(rs));
            }
            rs.close();
            statement.close();
        } catch (SQLException ex) {
            try {
                connection.rollback();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            ex.printStackTrace();
        } finally {
            close();
        }
        return productList;
    }

    @Override
    public void update(Product entity) {
        PreparedStatement preparedStatement = null;
        try {
            connection.setAutoCommit(false);
            preparedStatement = connection.prepareStatement("UPDATE deleted_products SET name = ?, description = ?," +
                    "image_link = ?, price = ?, category_id = (SELECT id FROM categories WHERE name = ?)," +
                    " size_id = (SELECT id FROM sizes WHERE name = ?), color_id = (SELECT id FROM colors WHERE name =" +
                    " ?)");
            preparedStatement.setString(1, entity.getName());
            preparedStatement.setString(2, entity.getDescription());
            preparedStatement.setString(3, entity.getImageLink());
            preparedStatement.setInt(4, entity.getPrice());
            preparedStatement.setString(5, entity.getCategory());
            preparedStatement.setString(6, entity.getSize());
            preparedStatement.setString(7, entity.getColor());
            preparedStatement.executeUpdate();
            connection.commit();
            preparedStatement.close();
        } catch (SQLException ex) {
            try {
                connection.rollback();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            ex.printStackTrace();
        } finally {
            close();
        }
    }

    @Override
    public void delete(int id) {
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
                throwables.printStackTrace();
            }
            ex.printStackTrace();
        } finally {
            close();
        }
    }

    @Override
    public void close() {
        try {
            connection.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
