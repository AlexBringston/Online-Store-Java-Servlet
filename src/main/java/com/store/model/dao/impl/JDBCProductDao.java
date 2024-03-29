package com.store.model.dao.impl;

import com.store.model.dao.ProductDao;
import com.store.model.dao.Utils;
import com.store.model.dao.mapper.CategoryMapper;
import com.store.model.dao.mapper.ColorMapper;
import com.store.model.dao.mapper.ProductMapper;
import com.store.model.dao.mapper.SizeMapper;
import com.store.model.entity.Category;
import com.store.model.entity.Color;
import com.store.model.entity.Product;
import com.store.model.entity.Size;
import com.store.model.exception.DatabaseException;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Implementation of Product Dao
 * It has implementation of all generic Dao methods and some custom methods.
 *
 * @author Alexander Mulyk
 * @since 2021-06-14
 */
@SuppressWarnings("ALL")
public class JDBCProductDao implements ProductDao {

    private Connection connection;

    private static final Logger log = Logger.getLogger(JDBCProductDao.class);

    public JDBCProductDao(Connection connection) {
        this.connection = connection;
    }


    @Override
    public void create(Product entity) throws DatabaseException {
        ResultSet resultSet = null;
        PreparedStatement preparedStatement = null;
        try {
            connection.setAutoCommit(false);
            preparedStatement = connection.prepareStatement("INSERT INTO products (name, name_uk, image_link, " +
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
                log.trace("Could not rollback while trying to create a product");
            }
            throw new DatabaseException("Error with trying to create a product",ex);
        } finally {
            close();
        }
    }

    @Override
    public Optional<Product> findById(int id, String locale) throws DatabaseException {
        Product product = new Product();
        Statement statement = null;
        ResultSet rs = null;
        try {
            connection.setAutoCommit(false);
            ProductMapper mapper = new ProductMapper();
            statement = connection.createStatement();
            String SQL = "SELECT p.id, p.name%s, image_link, price, category_id, size_id, color_id, created_at, " +
                    " c.name%s as category, co.name%s as color, s.name%s as size " +
                    " from products p, categories c, colors co, sizes s WHERE p.id = %d and c.id = p.category_id " +
                    "and co.id = p.color_id and s.id = p.size_id";
            String newSQL = String.format(SQL, locale, locale, locale, locale, id);
            rs = statement.executeQuery(newSQL);
            if (rs.next()) {
                product = mapper.extractFromResultSet(rs, locale);
            }
            rs.close();
            statement.close();
        } catch (SQLException ex) {
            try {
                connection.rollback();
            } catch (SQLException throwables) {
                log.trace("Could not rollback while looking for a product");
            }
            throw new DatabaseException("Error with trying to find a product",ex);
        } finally {
            close();
        }
        return Optional.ofNullable(product);
    }

    @Override
    public Optional<List<Product>> findAll() throws DatabaseException {
        List<Product> productList = new ArrayList<>();
        Statement statement = null;
        ResultSet rs = null;
        try {
            connection.setAutoCommit(false);
            ProductMapper mapper = new ProductMapper();
            statement = connection.createStatement();
            String sql = "SELECT p.id, p.name, image_link, price, category_id, size_id, color_id, created_at, " +
                    " c.name as category, co.name as color, s.name as size" +
                    " from products p, categories c, colors co, sizes s";
            rs = statement.executeQuery(sql);
            while (rs.next()) {
                productList.add(mapper.extractFromResultSet(rs, ""));
            }
            rs.close();
            statement.close();
        } catch (SQLException ex) {
            try {
                connection.rollback();
            } catch (SQLException throwables) {
                log.trace("Could not rollback while looking for all products");
            }
            throw new DatabaseException("Error with trying to find all products",ex);
        } finally {
            close();
        }
        return Optional.ofNullable(productList);
    }

    @Override
    public void update(Product entity) throws DatabaseException {
        PreparedStatement preparedStatement = null;
        try {
            connection.setAutoCommit(false);
            preparedStatement = connection.prepareStatement("UPDATE products SET name = ?, name_uk = ?, image_link = " +
                    "?, price = ?," +
                    " category_id = (SELECT id FROM categories WHERE name = ?)," +
                    " size_id = (SELECT id FROM sizes WHERE name = ?), color_id = (SELECT id FROM colors WHERE name =" +
                    " " +
                    "?) WHERE id = ?");
            preparedStatement.setString(1, entity.getName());
            preparedStatement.setString(2, entity.getNameUK());
            preparedStatement.setString(3, entity.getImageLink());
            preparedStatement.setBigDecimal(4, entity.getPrice());
            preparedStatement.setString(5, entity.getCategory());
            preparedStatement.setString(6, entity.getSize());
            preparedStatement.setString(7, entity.getColor());
            preparedStatement.setInt(8, entity.getId());
            preparedStatement.executeUpdate();
            connection.commit();
            preparedStatement.close();
        } catch (SQLException ex) {
            try {
                connection.rollback();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            throw new DatabaseException("Error with trying to update product info",ex);
        } finally {
            close();
        }
    }

    @Override
    public void delete(int id) throws DatabaseException {
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
                log.trace("Problem with deleting a product");
            }
            throw new DatabaseException("Error with trying to delete a product",ex);
        } finally {
            close();
        }
    }

    @Override
    public void close() {
        try {
            connection.close();
        } catch (SQLException throwables) {
            log.trace("Could not close connection");
        }
    }

    /**
     * Method to count a number of products in system
     * @return number of products
     * @throws DatabaseException if error has occured while performing sql query or while mapping data
     */
    @Override
    public int countAllProducts() throws DatabaseException {
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
                log.trace("Problem with rollback");
            }
            throw new DatabaseException("Error with trying to count products",ex);
        } finally {
            close();
        }

        return count;
    }

    /**
     * Method to count products of certain category
     * @param category name of category
     * @return number of products of given category
     * @throws DatabaseException
     */
    @Override
    public int countProductsByCategory(String category) throws DatabaseException {
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
                log.trace("Could not count products by category");
            }
            throw new DatabaseException("Error with trying to count products by category",ex);
        } finally {
            close();
        }

        return count;
    }

    /**
     * Method to count products of certain color
     * @param color name of category
     * @return number of products of given color
     * @throws DatabaseException
     */
    @Override
    public int countProductsByColor(String color) throws DatabaseException {
        int count = 0;

        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        try {
            connection.setAutoCommit(false);
            preparedStatement = connection.prepareStatement("SELECT COUNT(*) FROM products where color_id = " +
                            "(SELECT id FROM colors where name = ?)");
            preparedStatement.setString(1, color);
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
                log.trace("Could not count products by color");
            }
            throw new DatabaseException("Error with trying to count products by color",ex);
        } finally {
            close();
        }

        return count;
    }

    /**
     * Method to count products of certain size
     * @param size name of category
     * @return number of products of given size
     * @throws DatabaseException
     */
    @Override
    public int countProductsBySize(String size) throws DatabaseException {
        int count = 0;

        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        try {
            connection.setAutoCommit(false);
            preparedStatement = connection.prepareStatement("SELECT COUNT(*) FROM products where size_id = " +
                    "(SELECT id FROM sizes where name = ?)");
            preparedStatement.setString(1, size);
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
                log.trace("Could not count products by size");
            }
            throw new DatabaseException("Error with trying to count products by size",ex);
        } finally {
            close();
        }

        return count;
    }

    /**
     * Method to get a list of products for current page
     * @param count page number
     * @param limit limit of products per page
     * @param orderBy parameter for sorting
     * @param orderDirection order direction
     * @param locale locale name
     * @return optional of list of products
     * @throws DatabaseException
     */
    @Override
    public Optional<List<Product>> findPerPage(int count, int limit, String orderBy, String orderDirection,
                                               String locale) throws DatabaseException {
        List<Product> productList = new ArrayList<>();
        Statement statement = null;
        ResultSet rs = null;
        try {
            connection.setAutoCommit(false);
            ProductMapper mapper = new ProductMapper();
            int offset = (count - 1) * limit;
            String SQL = "SELECT p.id, p.name%s, image_link, price, category_id, size_id, color_id, created_at, " +
                    "c.name%s as category, co.name%s as color, s.name%s as size " +
                    "from products p, categories c, colors co, sizes s " +
                    "where c.id = p.category_id and " +
                    "co.id = p.color_id and s.id = p.size_id ORDER BY p.%s %s LIMIT %d OFFSET %d";
            statement = connection.createStatement();
            rs = statement.executeQuery(String.format(SQL, locale, locale, locale, locale, orderBy, orderDirection,
                    limit, offset));
            while (rs.next()) {
                productList.add(mapper.extractFromResultSet(rs, locale));
            }
            rs.close();
            statement.close();
        } catch (SQLException ex) {
            try {
                connection.rollback();
            } catch (SQLException throwables) {
                log.trace("Could not rollback");
            }
            throw new DatabaseException("Error with trying to list products per page",ex);
        } finally {
            close();
        }
        return Optional.of(productList);
    }

    /**
     * Method to get a list of products for current page
     * @param count page number
     * @param limit limit of products per page
     * @param orderBy parameter for sorting
     * @param orderDirection order direction
     * @param locale locale name
     * @return optional of list of products
     * @throws DatabaseException
     */
    @Override
    public Optional<List<Product>> findPerPageByCategory(int count, String name, String orderBy, String orderDirection,
                                               String locale) throws DatabaseException {
        List<Product> productList = new ArrayList<>();
        Statement statement = null;
        ResultSet rs = null;
        try {
            connection.setAutoCommit(false);
            ProductMapper mapper = new ProductMapper();
            int offset = (count - 1) * Utils.PRODUCTS_PER_PAGE;
            String SQL = "SELECT p.id, p.name%s, image_link, price, category_id, size_id, color_id, created_at," +
                    " c.name%s as category, co.name%s as color, s.name%s as size\n" +
                    "from products p, categories c, colors co, sizes s\n" +
                    "where (c.name = \'%s\' or c.name_uk = \'%s\' ) \n" +
                    "  and c.id = p.category_id\n" +
                    "  and co.id = p.color_id\n" +
                    "  and s.id = p.size_id\n" +
                    "ORDER BY p.%s %s\n" +
                    "LIMIT %d OFFSET %d";
            String newSQL = String.format(SQL, locale, locale, locale, locale, name, name, orderBy, orderDirection,
                    Utils.PRODUCTS_PER_PAGE,
                    offset);
            statement = connection.createStatement();
            rs = statement.executeQuery(newSQL);
            while (rs.next()) {
                productList.add(mapper.extractFromResultSet(rs, locale));
            }
            rs.close();
            statement.close();
        } catch (SQLException ex) {
            try {
                connection.rollback();
            } catch (SQLException throwables) {
                log.trace("Could not rollback in find per page by category");
            }
            throw new DatabaseException("Error with trying to list products per page by category",ex);
        } finally {
            close();
        }
        return Optional.of(productList);
    }

    /**
     * Method to get a list of products of certain color for current page
     * @param count page number
     * @param name color name
     * @param orderBy parameter for sorting
     * @param orderDirection order direction
     * @param locale locale name
     * @return optional of list of products
     * @throws DatabaseException
     */
    @Override
    public Optional<List<Product>> findPerPageByColor(int count, String name, String orderBy, String orderDirection,
                                                      String locale) throws DatabaseException {
        List<Product> productList = new ArrayList<>();
        Statement statement = null;
        ResultSet rs = null;
        try {
            connection.setAutoCommit(false);
            ProductMapper mapper = new ProductMapper();
            int offset = (count - 1) * Utils.PRODUCTS_PER_PAGE;
            String SQL = "SELECT p.id, p.name%s, image_link, price, category_id, size_id, color_id, created_at," +
                    " c.name%s as category, co.name%s as color, s.name%s as size\n" +
                    "from products p,\n" +
                    "     categories c,\n" +
                    "     colors co,\n" +
                    "     sizes s\n" +
                    "where (co.name = \'%s\' or co.name_uk = \'%s\' ) \n" +
                    "  and c.id = p.category_id\n" +
                    "  and co.id = p.color_id\n" +
                    "  and s.id = p.size_id\n" +
                    "ORDER BY p.%s %s\n" +
                    "LIMIT %d OFFSET %d";
            String newSQL = String.format(SQL, locale, locale, locale, locale, name, name, orderBy, orderDirection,
                    Utils.PRODUCTS_PER_PAGE,
                    offset);
            statement = connection.createStatement();
            rs = statement.executeQuery(newSQL);
            while (rs.next()) {
                productList.add(mapper.extractFromResultSet(rs, locale));
            }
            rs.close();
            statement.close();
        } catch (SQLException ex) {
            try {
                connection.rollback();
            } catch (SQLException throwables) {
                log.trace("Could not rollback in find per page by color");
            }
            throw new DatabaseException("Error with trying to list products per page by color",ex);
        } finally {
            close();
        }
        return Optional.of(productList);
    }

    /**
     * Method to get a list of products of certain size for current page
     * @param count page number
     * @param name size name
     * @param orderBy parameter for sorting
     * @param orderDirection order direction
     * @param locale locale name
     * @return optional of list of products
     * @throws DatabaseException
     */
    @Override
    public Optional<List<Product>> findPerPageBySize(int count, String name, String orderBy, String orderDirection,
                                                     String locale) throws DatabaseException {
        List<Product> productList = new ArrayList<>();
        Statement statement = null;
        ResultSet rs = null;
        try {
            connection.setAutoCommit(false);
            ProductMapper mapper = new ProductMapper();
            int offset = (count - 1) * Utils.PRODUCTS_PER_PAGE;
            String SQL = "SELECT p.id, p.name%s, image_link, price, category_id, size_id, color_id, created_at," +
                    " c.name%s as category, co.name%s as color, s.name%s as size\n" +
                    "from products p,\n" +
                    "     categories c,\n" +
                    "     colors co,\n" +
                    "     sizes s\n" +
                    "where (s.name = \'%s\' or s.name_uk = \'%s\' )\n" +
                    "  and c.id = p.category_id\n" +
                    "  and co.id = p.color_id\n" +
                    "  and s.id = p.size_id\n" +
                    "ORDER BY p.%s %s\n" +
                    "LIMIT %d OFFSET %d";
            statement = connection.createStatement();
            String newSQL = String.format(SQL, locale, locale, locale, locale, name, name, orderBy,
                    orderDirection,
                    Utils.PRODUCTS_PER_PAGE,
                    offset);
            statement = connection.createStatement();
            rs = statement.executeQuery(newSQL);
            while (rs.next()) {
                productList.add(mapper.extractFromResultSet(rs, locale));
            }
            rs.close();
            statement.close();
        } catch (SQLException ex) {
            try {
                connection.rollback();
            } catch (SQLException throwables) {
                log.trace("Could not rollback in find per page by size");
            }
            throw new DatabaseException("Error with trying to list products per page by size",ex);
        } finally {
            close();
        }
        return Optional.of(productList);
    }

    /**
     * Method to get a list of all categories
     * @param locale
     * @return
     * @throws DatabaseException
     */
    @Override
    public Optional<List<Category>> listAllCategories(String locale) throws DatabaseException {
        List<Category> categoryList = new ArrayList<>();
        Statement statement = null;
        ResultSet rs = null;
        try {
            connection.setAutoCommit(false);
            CategoryMapper mapper = new CategoryMapper();
            statement = connection.createStatement();
            String SQL = "SELECT id, name%s FROM categories ORDER BY id";
            rs = statement.executeQuery(String.format(SQL, locale));
            while (rs.next()) {
                categoryList.add(mapper.extractFromResultSet(rs, locale));
            }
            rs.close();
            statement.close();
        } catch (SQLException ex) {
            try {
                connection.rollback();
            } catch (SQLException throwables) {
                log.trace("Could not rollback in list all categories");
            }
            throw new DatabaseException("Error with trying to list all categories",ex);
        } finally {
            close();
        }
        return Optional.of(categoryList);
    }

    /**
     * Method to get a list of all colors
     * @param locale
     * @return
     * @throws DatabaseException
     */
    @Override
    public Optional<List<Color>> listAllColors(String locale) throws DatabaseException {
        List<Color> colorList = new ArrayList<>();
        Statement statement = null;
        ResultSet rs = null;
        try {
            connection.setAutoCommit(false);
            ColorMapper mapper = new ColorMapper();
            statement = connection.createStatement();
            String SQL = "SELECT id, name%s FROM colors ORDER BY id";
            rs = statement.executeQuery(String.format(SQL, locale));
            while (rs.next()) {
                colorList.add(mapper.extractFromResultSet(rs, locale));
            }
            rs.close();
            statement.close();
        } catch (SQLException ex) {
            try {
                connection.rollback();
            } catch (SQLException throwables) {
                log.trace("Could not rollback in list all colors");
            }
            throw new DatabaseException("Error with trying to list all colors",ex);
        } finally {
            close();
        }
        return Optional.of(colorList);
    }

    /**
     * Method to get a list of all sizes
     * @param locale
     * @return
     * @throws DatabaseException
     */
    @Override
    public Optional<List<Size>> listAllSizes(String locale) throws DatabaseException {
        List<Size> sizeList = new ArrayList<>();
        Statement statement = null;
        ResultSet rs = null;
        try {
            connection.setAutoCommit(false);
            SizeMapper mapper = new SizeMapper();
            statement = connection.createStatement();
            String SQL = "SELECT id, name%s FROM sizes ORDER BY id";
            rs = statement.executeQuery(String.format(SQL, locale));
            while (rs.next()) {
                sizeList.add(mapper.extractFromResultSet(rs, locale));
            }
            rs.close();
            statement.close();
        } catch (SQLException ex) {
            try {
                connection.rollback();
            } catch (SQLException throwables) {
                log.trace("Could not rollback in list all sizes");
            }
            throw new DatabaseException("Error with trying to list all sizes",ex);
        } finally {
            close();
        }
        return Optional.of(sizeList);
    }
}
