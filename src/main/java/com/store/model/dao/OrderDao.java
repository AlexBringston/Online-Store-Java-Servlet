package com.store.model.dao;

import com.store.model.entity.Order;
import com.store.model.exception.DatabaseException;

import java.util.List;
import java.util.Optional;

public interface OrderDao extends GenericDao<Order> {
    Optional<List<Order>> findOrdersOfUser(int userId, int pageNumber, int limit) throws DatabaseException;

    int countAllOrdersOfUser(int userId) throws DatabaseException;

    int countAllOrders() throws DatabaseException;

    Optional<List<Order>> findOrdersPerPage(int pageNumber, int limit) throws DatabaseException;
}
