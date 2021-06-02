package com.store.model.dao.impl;

import com.store.model.dao.ProductDao;
import com.store.model.dao.mapper.CategoryMapper;
import com.store.model.dao.mapper.ColorMapper;
import com.store.model.dao.mapper.ProductMapper;
import com.store.model.dao.mapper.SizeMapper;
import com.store.model.entity.Category;
import com.store.model.entity.Color;
import com.store.model.entity.Product;
import com.store.model.entity.Size;
import com.store.model.service.ProductService;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("ALL")
public class JDBCProductDao implements ProductDao {

    private Connection connection;

    private static final Logger log = Logger.getLogger(ProductService.class);

    public JDBCProductDao(Connection connection) {
        this.connection = connection;
    }


    @Override
    public void create(Product entity) {
        ResultSet resultSet = null;
        PreparedStatement preparedStatement = null;
        try {
            connection.setAutoCommit(false);
            preparedStatement = connection.prepareStatement("INSERT INTO products (name, description, image_link, " +
                            "price, category_id, size_id, color_id) VALUES (?,?,?,?," +
                            "(SELECT id FROM categories WHERE name = ?)," +
                            "(SELECT id FROM size WHERE name = ?)," +
                            "(SELECT id FROM colors WHERE name = ?) )",
                    Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, entity.getName());
            preparedStatement.setString(1, entity.getDescription());
            preparedStatement.setString(1, entity.getImageLink());
            preparedStatement.setInt(1, entity.getPrice());
            preparedStatement.setString(1, entity.getCategory());
            preparedStatement.setString(1, entity.getSize());
            preparedStatement.setString(1, entity.getColor());
            preparedStatement.executeUpdate();
            resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet.next()) {
                entity.setId(resultSet.getLong("id"));
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
                    ".name as size from products p, categories c, colors co, sizes s WHERE p.id = %d and c.id = p" +
                    ".category_id and " +
                    "co.id = p.color_id and s.id = p.size_id";
            rs = statement.executeQuery(String.format(SQL,id));
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
            rs = statement.executeQuery("SELECT * FROM products");
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
            preparedStatement = connection.prepareStatement("UPDATE products SET name = ?, description = ?," +
                    "image_link = ?, price = ?, category_id = (SELECT id FROM categories WHERE name = ?)," +
                    " size_id = (SELECT id FROM size WHERE name = ?), color_id = (SELECT id FROM colors WHERE name = ?)");
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
            preparedStatement = connection.prepareStatement("DELETE FROM products WHERE id = ?");
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

    @Override
    public int countAllProducts() {
        int count = 0;

        Statement statement = null;
        ResultSet rs = null;
        try {
            connection.setAutoCommit(false);
            statement = connection.createStatement();
            rs = statement.executeQuery("SELECT COUNT(*) FROM products");
            if (rs.next()) {
                count = rs.getInt(1);
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

        return count;
    }

    @Override
    public int countProductsByCategory(String category) {
        int count = 0;

        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        try {
            connection.setAutoCommit(false);
            preparedStatement = connection.prepareStatement("SELECT COUNT(*) FROM products where category_id = (SELECT id FROM categories" +
                    " where name = ?)");
            preparedStatement.setString(1, category);
            rs = preparedStatement.executeQuery();
            if (rs.next()) {
                count = rs.getInt(1);
            }
            rs.close();
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

        return count;
    }

    @Override
    public int countProductsByColor(String category) {
        int count = 0;

        Statement statement = null;
        ResultSet rs = null;
        try {
            connection.setAutoCommit(false);
            statement = connection.createStatement();
            rs = statement.executeQuery("SELECT COUNT(*) FROM products");
            if (rs.next()) {
                count = rs.getInt(1);
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

        return count;
    }

    @Override
    public int countProductsBySize(String category) {
        int count = 0;

        Statement statement = null;
        ResultSet rs = null;
        try {
            connection.setAutoCommit(false);
            statement = connection.createStatement();
            rs = statement.executeQuery("SELECT COUNT(*) FROM products");
            if (rs.next()) {
                count = rs.getInt(1);
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

        return count;
    }

    @Override
    public List<Product> findPerPage(int count, String orderBy, String orderDirection) {
        List<Product> productList = new ArrayList<>();
        Statement statement = null;
        ResultSet rs = null;
        try {
            connection.setAutoCommit(false);
            ProductMapper mapper = new ProductMapper();
            int offset = (count - 1) * 8;
            String SQL = "SELECT p.*, c.name as category, co.name as color, s" +
                    ".name as size from products p, categories c, colors co, sizes s where c.id = p.category_id and " +
                    "co.id = p.color_id and s.id = p.size_id ORDER BY %s %s LIMIT 8 OFFSET %d";
            statement = connection.createStatement();
            rs = statement.executeQuery(String.format(SQL, orderBy, orderDirection, offset));
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
    public List<Product> findPerPageByCategory(int count, String name, String orderBy, String orderDirection) {
        List<Product> productList = new ArrayList<>();
        Statement statement = null;
        ResultSet rs = null;
        try {
            connection.setAutoCommit(false);
            ProductMapper mapper = new ProductMapper();
            int offset = (count - 1) * 8;
            String SQL = "SELECT p.*, c.name as category, co.name as color, s.name as size\n" +
                    "from products p,\n" +
                    "     categories c,\n" +
                    "     colors co,\n" +
                    "     sizes s\n" +
                    "where c.name = \'%s\' \n" +
                    "  and c.id = p.category_id\n" +
                    "  and co.id = p.color_id\n" +
                    "  and s.id = p.size_id\n" +
                    "ORDER BY %s %s\n" +
                    "LIMIT 8 OFFSET %d";
            statement = connection.createStatement();
            rs = statement.executeQuery(String.format(SQL, name, orderBy, orderDirection, offset));
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
    public List<Product> findPerPageByColor(int count, String name, String orderBy, String orderDirection) {
        List<Product> productList = new ArrayList<>();
        Statement statement = null;
        ResultSet rs = null;
        try {
            connection.setAutoCommit(false);
            ProductMapper mapper = new ProductMapper();
            int offset = (count - 1) * 8;
            String SQL = "SELECT p.*, c.name as category, co.name as color, s.name as size\n" +
                    "from products p,\n" +
                    "     categories c,\n" +
                    "     colors co,\n" +
                    "     sizes s\n" +
                    "where co.name = \'%s\' \n" +
                    "  and c.id = p.category_id\n" +
                    "  and co.id = p.color_id\n" +
                    "  and s.id = p.size_id\n" +
                    "ORDER BY %s %s\n" +
                    "LIMIT 8 OFFSET %d";
            statement = connection.createStatement();
            log.trace(name + "---" + orderBy + "---" + orderDirection + "---" + offset);
            rs = statement.executeQuery(String.format(SQL, name, orderBy, orderDirection, offset));
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
    public List<Product> findPerPageBySize(int count, String name, String orderBy, String orderDirection) {
        List<Product> productList = new ArrayList<>();
        Statement statement = null;
        ResultSet rs = null;
        try {
            connection.setAutoCommit(false);
            ProductMapper mapper = new ProductMapper();
            int offset = (count - 1) * 8;
            String SQL = "SELECT p.*, c.name as category, co.name as color, s.name as size\n" +
                    "from products p,\n" +
                    "     categories c,\n" +
                    "     colors co,\n" +
                    "     sizes s\n" +
                    "where s.name = \'%s\' \n" +
                    "  and c.id = p.category_id\n" +
                    "  and co.id = p.color_id\n" +
                    "  and s.id = p.size_id\n" +
                    "ORDER BY %s %s\n" +
                    "LIMIT 8 OFFSET %d";
            statement = connection.createStatement();
            rs = statement.executeQuery(String.format(SQL, name, orderBy, orderDirection, offset));
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
    public List<Category> listAllCategories() {
        List<Category> categoryList = new ArrayList<>();
        Statement statement = null;
        ResultSet rs = null;
        try {
            connection.setAutoCommit(false);
            CategoryMapper mapper = new CategoryMapper();
            statement = connection.createStatement();
            rs = statement.executeQuery("SELECT * FROM categories ORDER BY id");
            while (rs.next()) {
                categoryList.add(mapper.extractFromResultSet(rs));
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
        return categoryList;
    }

    @Override
    public List<Color> listAllColors() {
        List<Color> colorList = new ArrayList<>();
        Statement statement = null;
        ResultSet rs = null;
        try {
            connection.setAutoCommit(false);
            ColorMapper mapper = new ColorMapper();
            statement = connection.createStatement();
            rs = statement.executeQuery("SELECT * FROM colors ORDER BY id");
            while (rs.next()) {
                colorList.add(mapper.extractFromResultSet(rs));
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
        return colorList;
    }

    @Override
    public List<Size> listAllSizes() {
        List<Size> sizeList = new ArrayList<>();
        Statement statement = null;
        ResultSet rs = null;
        try {
            connection.setAutoCommit(false);
            SizeMapper mapper = new SizeMapper();
            statement = connection.createStatement();
            rs = statement.executeQuery("SELECT * FROM sizes ORDER BY id");
            while (rs.next()) {
                sizeList.add(mapper.extractFromResultSet(rs));
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
        return sizeList;
    }


}
