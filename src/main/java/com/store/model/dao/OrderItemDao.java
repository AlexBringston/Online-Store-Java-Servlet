package com.store.model.dao;

import com.store.model.entity.OrderItem;

import java.math.BigDecimal;
import java.util.List;

public interface OrderItemDao extends GenericDao<OrderItem>{

    List<OrderItem> findAllItemsOfOrder(int orderId, int pageNumber);

    int countAllItemsInOrder(int orderId);

    BigDecimal countTotalCost(int orderId);
}
