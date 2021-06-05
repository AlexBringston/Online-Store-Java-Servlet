package com.store.model.dao.mapper;

import com.store.model.entity.Role;
import com.store.model.entity.User;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserMapper implements ObjectMapper<User>{
    @Override
    public User extractFromResultSet(ResultSet resultSet, String locale) throws SQLException {
        User user = new User();
        user.setId(resultSet.getInt("id"));
        user.setLogin(resultSet.getString("login"));
        user.setPassword(resultSet.getString("password"));
        user.setFirstName(resultSet.getString(String.format("first_name%s",locale)));
        user.setLastName(resultSet.getString(String.format("last_name%s",locale)));
        user.setCreatedAt(resultSet.getTimestamp("created_at"));
        user.setRoleId(resultSet.getInt("role_id"));
        user.setRole(Role.values()[user.getRoleId()]);
        user.setStatus(resultSet.getString(String.format("status%s",locale)));
        user.setBalance(new BigDecimal(resultSet.getString("balance")));
        return user;
    }
}
