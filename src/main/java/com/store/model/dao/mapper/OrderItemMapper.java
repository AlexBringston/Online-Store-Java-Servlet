package com.store.model.dao.mapper;

import com.store.model.entity.OrderItem;
import com.store.model.service.ProductService;

import java.sql.ResultSet;
import java.sql.SQLException;

public class OrderItemMapper implements ObjectMapper<OrderItem> {
    @Override
    public OrderItem extractFromResultSet(ResultSet resultSet) throws SQLException {
        OrderItem orderItem = new OrderItem();
        orderItem.setId(resultSet.getInt("id"));
        orderItem.setOrderId(resultSet.getInt("user_id"));
        orderItem.setProduct(new ProductService().getProductById(resultSet.getInt("product_id")));
        orderItem.setQuantity(resultSet.getInt("quantity"));
        return orderItem;
    }
}