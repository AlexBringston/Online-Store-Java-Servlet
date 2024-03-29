package com.store.model.dao.impl;

import com.store.model.dao.OrderItemDao;
import com.store.model.dao.Utils;
import com.store.model.dao.mapper.OrderItemMapper;
import com.store.model.entity.OrderItem;
import com.store.model.exception.DatabaseException;
import com.store.model.service.ProductService;
import org.apache.log4j.Logger;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Implementation of Order Item Dao
 * It has implementation of all generic Dao methods and some custom methods.
 *
 * @author Alexander Mulyk
 * @since 2021-06-14
 */
@SuppressWarnings("ALL")
public class JDBCOrderItemDao implements OrderItemDao {

    private Connection connection;

    private static final Logger log = Logger.getLogger(ProductService.class);

    public JDBCOrderItemDao(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void create(OrderItem entity) throws DatabaseException {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection.setAutoCommit(false);
            preparedStatement = connection.prepareStatement("INSERT INTO order_items (order_id, product_id, quantity)" +
                            " VALUES (?, ?, ?) ",
                    Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setLong(1, entity.getOrderId());
            preparedStatement.setLong(2, entity.getProduct().getId());
            preparedStatement.setLong(3, entity.getQuantity());
            preparedStatement.executeUpdate();
            resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet.next()) {
                entity.setId(resultSet.getInt("id"));
            }
            connection.commit();
            preparedStatement.close();
        } catch (SQLException ex) {
            log.trace(ex.getMessage());
            try {
                connection.rollback();
            } catch (SQLException throwables) {
                log.trace("Could not create order item");
            }

            throw new DatabaseException("Error with trying to create order item", ex);
        }
    }

    @Override
    public Optional<OrderItem> findById(int id, String locale) throws DatabaseException {
        OrderItem orderItem = new OrderItem();
        Statement statement = null;
        ResultSet rs = null;
        try {
            connection.setAutoCommit(false);
            OrderItemMapper mapper = new OrderItemMapper();
            statement = connection.createStatement();
            String SQL = "SELECT * FROM order_items WHERE id = ?";
            rs = statement.executeQuery(String.format(SQL, id));
            if (rs.next()) {
                orderItem = mapper.extractFromResultSet(rs,locale);
            }
            rs.close();
            statement.close();
        } catch (SQLException ex) {
            try {
                connection.rollback();
            } catch (SQLException throwables) {
                log.trace("Could not find order item by id");
            }
            throw new DatabaseException("Error with trying to find order item by id", ex);
        }
        return Optional.ofNullable(orderItem);
    }

    @Override
    public Optional<List<OrderItem>> findAll() throws DatabaseException {
        List<OrderItem> orderItemList = new ArrayList<>();
        Statement statement = null;
        ResultSet rs = null;
        try {
            connection.setAutoCommit(false);
            OrderItemMapper mapper = new OrderItemMapper();
            statement = connection.createStatement();
            rs = statement.executeQuery("SELECT * FROM order_items");
            while (rs.next()) {
                orderItemList.add(mapper.extractFromResultSet(rs,""));
            }
            connection.commit();
            rs.close();
            statement.close();
        } catch (SQLException ex) {
            try {
                connection.rollback();
            } catch (SQLException throwables) {
                log.trace("Could not find all order items");
            }
            throw new DatabaseException("Error with trying to find all order items", ex);
        }
        return Optional.ofNullable(orderItemList);
    }

    @Override
    public void update(OrderItem entity) throws DatabaseException {
        PreparedStatement preparedStatement = null;
        try {
            connection.setAutoCommit(false);
            preparedStatement = connection.prepareStatement("UPDATE order_items SET order_id = ?, product_id = ?," +
                    " quantity = ?");
            preparedStatement.setLong(1, entity.getOrderId());
            preparedStatement.setLong(1, entity.getProduct().getId());
            preparedStatement.setLong(1, entity.getQuantity());
            preparedStatement.executeUpdate();

            connection.commit();
            preparedStatement.close();
        } catch (SQLException ex) {
            try {
                connection.rollback();
            } catch (SQLException throwables) {
                log.trace("Could not update order item");
            }
            throw new DatabaseException("Error with trying to update order item", ex);
        }
    }

    @Override
    public void delete(int id) throws DatabaseException {
        PreparedStatement preparedStatement = null;
        try {
            connection.setAutoCommit(false);
            preparedStatement = connection.prepareStatement("DELETE FROM order_items WHERE id = ?");
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();

            connection.commit();
            preparedStatement.close();
        } catch (SQLException ex) {
            try {
                connection.rollback();
            } catch (SQLException throwables) {
                log.trace("Could not find delete order item");
            }
            throw new DatabaseException("Error with trying to delete order item", ex);
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
     * Method to get a list of order items of chosen order and show it on page
     * @param orderId current order id
     * @param pageNumber number of current page
     * @param locale locale name
     * @return optional of list of order items
     * @throws DatabaseException if error in database is occured
     */
    @Override
    public Optional<List<OrderItem>> findAllItemsOfOrder(int orderId, int pageNumber, String locale) throws DatabaseException {
        List<OrderItem> orderItemList = new ArrayList<>();
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        try {
            connection.setAutoCommit(false);
            OrderItemMapper mapper = new OrderItemMapper();
            int offset = (pageNumber - 1) * Utils.ORDER_ITEMS_PER_PAGE;
            preparedStatement = connection.prepareStatement("SELECT * FROM order_items WHERE order_id = ? LIMIT ? " +
                    "OFFSET ?");
            preparedStatement.setInt(1, orderId);
            preparedStatement.setInt(2, Utils.ORDER_ITEMS_PER_PAGE);
            preparedStatement.setInt(3, offset);
            rs = preparedStatement.executeQuery();
            while (rs.next()) {
                orderItemList.add(mapper.extractFromResultSet(rs,locale));
            }
            connection.commit();
            rs.close();
            preparedStatement.close();
        } catch (SQLException ex) {
            try {
                connection.rollback();
            } catch (SQLException throwables) {
                log.trace("Could not find items of order");
            }
            throw new DatabaseException("Error with trying to find items of order", ex);
        }
        return Optional.of(orderItemList);
    }

    /**
     * Method to count a number of order items in given order
     * @param orderId id of given order
     * @return amount of items in order
     * @throws DatabaseException
     */
    @Override
    public int countAllItemsInOrder(int orderId) throws DatabaseException {
        int count = 0;
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        try {
            connection.setAutoCommit(false);
            preparedStatement = connection.prepareStatement("SELECT COUNT(*) FROM order_items WHERE order_id = ?");
            preparedStatement.setInt(1, orderId);
            rs = preparedStatement.executeQuery();
            if (rs.next()) {
                count = rs.getInt(1);
            }
            connection.commit();
            rs.close();
            preparedStatement.close();
        } catch (SQLException ex) {
            try {
                connection.rollback();
            } catch (SQLException throwables) {
                log.trace("Could not find orders of user");
            }
            throw new DatabaseException("Error with trying to count items in order", ex);
        }
        return count;
    }

    /**
     * Method to count total cost of given order
     * @param orderId id of given order
     * @return BigDecimal number which stores a total cost of order
     * @throws DatabaseException if error has occured in SQL
     */
    @Override
    public BigDecimal countTotalCost(int orderId) throws DatabaseException {
        BigDecimal count = BigDecimal.ZERO;
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        try {
            connection.setAutoCommit(false);
            OrderItemMapper mapper = new OrderItemMapper();
            preparedStatement = connection.prepareStatement("SELECT * FROM order_items WHERE order_id = ? ");
            preparedStatement.setInt(1, orderId);
            rs = preparedStatement.executeQuery();
            while (rs.next()) {
                OrderItem orderItem = mapper.extractFromResultSet(rs,"");
                count =
                        count.add(orderItem.getProduct().getPrice().multiply(BigDecimal.valueOf(orderItem.getQuantity())));
            }
            connection.commit();
            rs.close();
            preparedStatement.close();
        } catch (SQLException ex) {
            try {
                connection.rollback();
            } catch (SQLException throwables) {
                log.trace("Could not find count total cost");
            }
            throw new DatabaseException("Error with trying to count total cost of order", ex);
        }
        return count;
    }
}
