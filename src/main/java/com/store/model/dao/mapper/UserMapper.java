package com.store.model.dao.mapper;

import com.store.model.entity.User;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserMapper implements ObjectMapper<User>{
    @Override
    public User extractFromResultSet(ResultSet resultSet) throws SQLException {
        User user = new User();
        user.setId(resultSet.getInt("id"));
        user.setLogin(resultSet.getString("login"));
        user.setPassword(resultSet.getString("password"));
        user.setFirstName(resultSet.getString("first_name"));
        user.setLastName(resultSet.getString("last_name"));
        user.setCreatedAt(resultSet.getTimestamp("created_at"));
        user.setRoleId(resultSet.getInt("role_id"));
        user.setStatus(resultSet.getString("status"));
        return user;
    }
}
