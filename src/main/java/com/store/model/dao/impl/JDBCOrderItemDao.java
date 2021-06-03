package com.store.model.dao.impl;

import com.store.model.dao.OrderItemDao;
import com.store.model.dao.mapper.OrderItemMapper;
import com.store.model.entity.OrderItem;
import com.store.model.service.ProductService;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JDBCOrderItemDao implements OrderItemDao {

    private Connection connection;

    private static final Logger log = Logger.getLogger(ProductService.class);

    public JDBCOrderItemDao(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void create(OrderItem entity) {
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
    public OrderItem findById(int id) {
        OrderItem orderItem = new OrderItem();
        Statement statement = null;
        ResultSet rs = null;
        try {
            connection.setAutoCommit(false);
            OrderItemMapper mapper = new OrderItemMapper();
            statement = connection.createStatement();
            String SQL = "SELECT * FROM order_items WHERE id = ?";
            rs = statement.executeQuery(String.format(SQL,id));
            if (rs.next()) {
                orderItem = mapper.extractFromResultSet(rs);
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
        return orderItem;
    }

    @Override
    public List<OrderItem> findAll() {
        List<OrderItem> orderItemList = new ArrayList<>();
        Statement statement = null;
        ResultSet rs = null;
        try {
            connection.setAutoCommit(false);
            OrderItemMapper mapper = new OrderItemMapper();
            statement = connection.createStatement();
            rs = statement.executeQuery("SELECT * FROM order_items");
            while (rs.next()) {
                orderItemList.add(mapper.extractFromResultSet(rs));
            }
            connection.commit();
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
        return orderItemList;
    }

    @Override
    public void update(OrderItem entity) {
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
            preparedStatement = connection.prepareStatement("DELETE FROM order_items WHERE id = ?");
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
