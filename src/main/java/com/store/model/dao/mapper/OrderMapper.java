package com.store.model.dao.mapper;

import com.store.model.entity.Order;

import java.sql.ResultSet;
import java.sql.SQLException;

public class OrderMapper implements ObjectMapper<Order>{
    @Override
    public Order extractFromResultSet(ResultSet resultSet) throws SQLException {
        Order order = new Order();
        order.setId(resultSet.getInt("id"));
        order.setUserId(resultSet.getInt("user_id"));
        order.setCreatedAt(resultSet.getTimestamp("created_at"));
        return order;
    }
}
