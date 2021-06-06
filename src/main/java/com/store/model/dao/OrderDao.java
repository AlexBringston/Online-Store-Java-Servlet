package com.store.model.dao;

import com.store.model.entity.Order;
import com.store.model.exception.DatabaseException;

import java.util.List;

public interface OrderDao extends GenericDao<Order> {
    List<Order> findOrdersOfUser(int userId, int pageNumber, int limit) throws DatabaseException;

    int countAllOrdersOfUser(int userId) throws DatabaseException;

    int countAllOrders() throws DatabaseException;

    List<Order> findOrdersPerPage(int pageNumber, int limit) throws DatabaseException;
}
