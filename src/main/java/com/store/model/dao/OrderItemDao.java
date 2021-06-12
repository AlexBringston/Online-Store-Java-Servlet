package com.store.model.dao;

import com.store.model.entity.OrderItem;
import com.store.model.exception.DatabaseException;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface OrderItemDao extends GenericDao<OrderItem>{

    Optional<List<OrderItem>> findAllItemsOfOrder(int orderId, int pageNumber, String locale) throws DatabaseException;

    int countAllItemsInOrder(int orderId) throws DatabaseException;

    BigDecimal countTotalCost(int orderId) throws DatabaseException;
}
