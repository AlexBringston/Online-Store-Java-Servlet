package com.store.model.dao.mapper;

import com.store.model.entity.OrderItem;
import com.store.model.exception.DatabaseException;
import com.store.model.service.ProductService;

import java.sql.ResultSet;
import java.sql.SQLException;

public class OrderItemMapper implements ObjectMapper<OrderItem> {
    @Override
    public OrderItem extractFromResultSet(ResultSet resultSet, String locale) throws SQLException, DatabaseException {
        OrderItem orderItem = new OrderItem();
        orderItem.setId(resultSet.getInt("id"));
        orderItem.setOrderId(resultSet.getInt("order_id"));
        orderItem.setProduct(new ProductService().getProductById(resultSet.getInt("product_id"),locale));
        orderItem.setQuantity(resultSet.getInt("quantity"));
        return orderItem;
    }
}
