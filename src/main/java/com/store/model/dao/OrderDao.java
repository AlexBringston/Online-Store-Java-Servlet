package com.store.model.dao;

import com.store.model.entity.Order;

import java.util.List;

public interface OrderDao extends GenericDao<Order> {
    List<Order> findOrdersOfUser(int userId, int pageNumber);

    int countAllOrdersOfUser(int userId);

}
