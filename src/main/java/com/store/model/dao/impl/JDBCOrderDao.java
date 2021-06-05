package com.store.model.dao.impl;

import com.store.model.dao.OrderDao;
import com.store.model.dao.Utils;
import com.store.model.dao.mapper.OrderMapper;
import com.store.model.entity.Order;
import com.store.model.service.ProductService;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JDBCOrderDao implements OrderDao {

    private Connection connection;

    private static final Logger log = Logger.getLogger(ProductService.class);

    public JDBCOrderDao(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void create(Order entity) {
        ResultSet resultSet = null;
        PreparedStatement preparedStatement = null;
        try {
            connection.setAutoCommit(false);
            preparedStatement = connection.prepareStatement("INSERT INTO orders (user_id) VALUES (?)",
                    Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setLong(1, entity.getUserId());
            preparedStatement.executeUpdate();
            resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet.next()) {
                entity.setId(resultSet.getInt("id"));
                entity.setCreatedAt(resultSet.getTimestamp("created_at"));
                entity.setStatus(resultSet.getString("status"));
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
    public Order findById(int id, String locale) {
        Order order = new Order();
        Statement statement = null;
        ResultSet rs = null;
        try {
            OrderMapper mapper = new OrderMapper();
            statement = connection.createStatement();
            String SQL = "SELECT * FROM orders WHERE id = %d";
            rs = statement.executeQuery(String.format(SQL,id));
            if (rs.next()) {
                order = mapper.extractFromResultSet(rs,"");
            }
            rs.close();
            statement.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            close();
        }
        return order;
    }

    @Override
    public List<Order> findAll() {
        List<Order> orderList = new ArrayList<>();
        Statement statement = null;
        ResultSet rs = null;
        try {
            connection.setAutoCommit(false);
            OrderMapper mapper = new OrderMapper();
            statement = connection.createStatement();
            rs = statement.executeQuery("SELECT * FROM orders");
            while (rs.next()) {
                orderList.add(mapper.extractFromResultSet(rs,""));
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
        return orderList;
    }

    @Override
    public void update(Order entity) {
        PreparedStatement preparedStatement = null;
        try {
            connection.setAutoCommit(false);
            preparedStatement = connection.prepareStatement("UPDATE orders SET user_id = ?, status = ? WHERE id = ?");
            preparedStatement.setLong(1, entity.getUserId());
            preparedStatement.setString(2, entity.getStatus());
            preparedStatement.setInt(3, entity.getId());
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
            preparedStatement = connection.prepareStatement("DELETE FROM orders WHERE id = ?");
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
    public List<Order> findOrdersOfUser(int userId, int pageNumber, int limit) {
        List<Order> orderList = new ArrayList<>();
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        try {
            connection.setAutoCommit(false);
            OrderMapper mapper = new OrderMapper();
            int offset = (pageNumber - 1) * limit;
            preparedStatement = connection.prepareStatement("SELECT * FROM orders WHERE user_id = ? ORDER BY " +
                    "created_at DESC" +
                    " LIMIT ? OFFSET ?");
            preparedStatement.setInt(1,userId);
            preparedStatement.setInt(2, limit);
            preparedStatement.setInt(3, offset);
            rs = preparedStatement.executeQuery();
            while (rs.next()) {
                orderList.add(mapper.extractFromResultSet(rs,""));
            }
            connection.commit();
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
        return orderList;
    }

    @Override
    public int countAllOrdersOfUser(int userId) {
        int count = 0;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            OrderMapper mapper = new OrderMapper();
            preparedStatement = connection.prepareStatement("SELECT COUNT(*) FROM orders WHERE user_id = ?");
            preparedStatement.setInt(1,userId);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                count = resultSet.getInt(1);
            }
            resultSet.close();
            preparedStatement.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            close();
        }
        return count;
    }

    @Override
    public int countAllOrders() {
        int count = 0;
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT COUNT(*) FROM orders");
            if (resultSet.next()) {
                count = resultSet.getInt(1);
            }
            resultSet.close();
            statement.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            close();
        }
        return count;
    }

    @Override
    public List<Order> findOrdersPerPage(int pageNumber, int limit) {
        List<Order> orderList = new ArrayList<>();
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        try {
            connection.setAutoCommit(false);
            OrderMapper mapper = new OrderMapper();
            int offset = (pageNumber - 1) * limit;
            preparedStatement = connection.prepareStatement("SELECT * FROM orders ORDER BY id LIMIT ? OFFSET ?");
            preparedStatement.setInt(1, limit);
            preparedStatement.setInt(2, offset);
            rs = preparedStatement.executeQuery();
            while (rs.next()) {
                orderList.add(mapper.extractFromResultSet(rs,""));
            }
            connection.commit();
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
        return orderList;
    }
}
