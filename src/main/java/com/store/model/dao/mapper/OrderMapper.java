package com.store.model.dao.mapper;

import com.store.model.entity.Order;
import com.store.model.exception.DatabaseException;
import com.store.model.service.UserService;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Order mapper. Is used to get data from database and assign it to instance.
 *
 * @author Alexander Mulyk
 * @since 2021-06-14
 */
public class OrderMapper implements ObjectMapper<Order>{
    @Override
    public Order extractFromResultSet(ResultSet resultSet, String locale) throws SQLException, DatabaseException {
        Order order = new Order();
        order.setId(resultSet.getInt("id"));
        order.setUserId(resultSet.getInt("user_id"));
        order.setUser(new UserService().findUserById(resultSet.getInt("user_id"), locale));
        order.setCreatedAt(resultSet.getTimestamp("created_at"));
        order.setStatus(resultSet.getString("status"));
        return order;
    }
}
