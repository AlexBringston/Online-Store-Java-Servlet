package com.store.model.dao;

import com.store.model.entity.Order;

import java.util.List;

public interface OrderDao extends GenericDao<Order> {
    List<Order> findOrdersOfUser(int userId, int pageNumber, int limit);

    int countAllOrdersOfUser(int userId);

    int countAllOrders();

    List<Order> findOrdersPerPage(int pageNumber, int limit);
}
