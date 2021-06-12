package com.store.model.dao.impl;

import com.store.model.dao.UserDao;
import com.store.model.dao.Utils;
import com.store.model.dao.mapper.OrderMapper;
import com.store.model.dao.mapper.UserMapper;
import com.store.model.entity.Order;
import com.store.model.entity.User;
import com.store.model.exception.DatabaseException;
import com.store.model.service.ProductService;
import org.apache.log4j.Logger;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

public class JDBCUserDao implements UserDao {

    private final Connection connection;

    private static final Logger log = Logger.getLogger(JDBCUserDao.class);

    public JDBCUserDao(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void create(User entity) throws DatabaseException {
        ResultSet resultSet = null;
        PreparedStatement preparedStatement = null;
        try {
            connection.setAutoCommit(false);
            preparedStatement = connection.prepareStatement("INSERT INTO users (login, password, first_name, " +
                            "last_name, status) VALUES (?, ?, ?, ?, ?)",
                    Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, entity.getLogin());
            preparedStatement.setString(2, entity.getPassword());
            preparedStatement.setString(3, entity.getFirstName());
            preparedStatement.setString(4, entity.getLastName());
            preparedStatement.setString(5, entity.getStatus());
            preparedStatement.executeUpdate();
            resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet.next()) {
                entity.setId(resultSet.getInt("id"));
                entity.setCreatedAt(resultSet.getTimestamp("created_at"));
                entity.setBalance(BigDecimal.ZERO);
            }
            connection.commit();
            preparedStatement.close();
            resultSet.close();
        } catch (SQLException ex) {
            try {
                connection.rollback();
            } catch (SQLException throwables) {
                log.trace("Could not create user");
            }
            throw new DatabaseException("Error with trying to create a user", ex);
        } finally {
            close();
        }
    }

    @Override
    public Optional<User> findById(int id, String locale) throws DatabaseException {
        User user = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            UserMapper mapper = new UserMapper();
            preparedStatement = connection.prepareStatement("SELECT u.*, r.name as role FROM users u, roles r " +
                    "WHERE u.role_id = r.id and u.id=?");
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                user = mapper.extractFromResultSet(resultSet,"");
            }
            resultSet.close();
            preparedStatement.close();
        } catch (SQLException ex) {
            throw new DatabaseException("Error: could not find user by id", ex);
        } finally {
            close();
        }
        return Optional.ofNullable(user);
    }

    @Override
    public Optional<User> findUserByLogin(String login) throws DatabaseException {
        User user = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            UserMapper mapper = new UserMapper();
            preparedStatement = connection.prepareStatement("SELECT u.*, r.name as role FROM users u, roles r WHERE u" +
                    ".role_id = r.id and u.login=?");
            preparedStatement.setString(1, login);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                user = mapper.extractFromResultSet(resultSet,"");
            }
            resultSet.close();
            preparedStatement.close();
        } catch (SQLException ex) {
            throw new DatabaseException("Error: could not find user by login", ex);
        } finally {
            close();

        }
        return Optional.ofNullable(user);
    }

    @Override
    public int countAllUsers() throws DatabaseException {
        int count = 0;
        Statement statement = null;
        ResultSet rs = null;
        try {
            statement = connection.createStatement();
            rs = statement.executeQuery("SELECT COUNT(*) FROM users");
            if (rs.next()) {
                count = rs.getInt(1);
            }
            rs.close();
            statement.close();
        } catch (SQLException ex) {
            throw new DatabaseException("Error: could not count all users", ex);
        } finally {
            close();
        }

        return count;
    }

    @Override
    public Optional<List<User>> listUsersPerPage(int pageNumber, int limit) throws DatabaseException {
        List<User> userList = new ArrayList<>();
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        try {
            connection.setAutoCommit(false);
            UserMapper mapper = new UserMapper();
            int offset = (pageNumber - 1) * limit;
            preparedStatement = connection.prepareStatement("SELECT u.*, r.name as role FROM users u, roles r WHERE " +
                    "u.role_id = r.id AND role_id = 1 LIMIT ? OFFSET ?");
            preparedStatement.setInt(1, limit);
            preparedStatement.setInt(2, offset);
            rs = preparedStatement.executeQuery();
            while (rs.next()) {
                System.out.println(mapper.extractFromResultSet(rs,""));
                userList.add(mapper.extractFromResultSet(rs,""));
            }
            connection.commit();
            rs.close();
            preparedStatement.close();
        } catch (SQLException ex) {
            try {
                connection.rollback();
            } catch (SQLException throwables) {
                log.trace("Could not list users per page");
            }
            throw new DatabaseException("Error with trying to list users on page", ex);
        } finally {
            close();
        }
        return Optional.of(userList);
    }

    @Override
    public Optional<List<User>> findAll() throws DatabaseException {
        List<User> userList = new ArrayList<>();
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            connection.setAutoCommit(false);
            UserMapper mapper = new UserMapper();
            statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM users");
            while (resultSet.next()) {
                userList.add(mapper.extractFromResultSet(resultSet,""));
            }
            connection.commit();
            resultSet.close();
            statement.close();
        } catch (SQLException ex) {
            try {
                connection.rollback();
            } catch (SQLException throwables) {
                log.trace("Could not find all users");
            }
            throw new DatabaseException("Error with trying to list all users", ex);
        } finally {
            close();

        }
        return Optional.ofNullable(userList);
    }

    @Override
    public void update(User entity) throws DatabaseException {
        PreparedStatement preparedStatement = null;
        try {
            connection.setAutoCommit(false);
            preparedStatement = connection.prepareStatement("UPDATE users SET login = ?, password = ?," +
                    "first_name = ?, last_name = ?, status = ?, balance = ? WHERE id = ?");
            preparedStatement.setString(1, entity.getLogin());
            preparedStatement.setString(2, entity.getPassword());
            preparedStatement.setString(3, entity.getFirstName());
            preparedStatement.setString(4, entity.getLastName());
            preparedStatement.setString(5, entity.getStatus());

            preparedStatement.setBigDecimal(6, entity.getBalance());
            preparedStatement.setInt(7, entity.getId());
            preparedStatement.executeUpdate();
            connection.commit();
            preparedStatement.close();
        } catch (SQLException ex) {
            try {
                connection.rollback();
            } catch (SQLException throwables) {
                log.trace("Could not update user");
            }
            throw new DatabaseException("Error with trying to update a user", ex);
        } finally {
            close();
        }
    }

    @Override
    public void delete(int id) throws DatabaseException {
        PreparedStatement preparedStatement = null;
        try {
            connection.setAutoCommit(false);
            preparedStatement = connection.prepareStatement("DELETE FROM users WHERE id = ?");
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();

            connection.commit();
            preparedStatement.close();
        } catch (SQLException ex) {
            try {
                connection.rollback();
            } catch (SQLException throwables) {
                log.trace("Could not delete user");
            }
            throw new DatabaseException("Error with trying to delete a user", ex);
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
}
