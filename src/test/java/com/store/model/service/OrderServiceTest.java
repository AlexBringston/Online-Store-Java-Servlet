package com.store.model.service;

import com.store.model.entity.Order;
import com.store.model.entity.User;
import com.store.model.exception.DatabaseException;
import org.junit.jupiter.api.Test;


import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class OrderServiceTest {

    @Test
    void listAllOrders() throws DatabaseException {
        assertFalse(new OrderService().listAllOrders().isEmpty());
    }

    @Test
    void listOrdersPerPage() throws DatabaseException {
        assertFalse(new OrderService().listOrdersPerPage(1,8).isEmpty());
    }

    @Test
    void countAllOrders() throws DatabaseException {
        assertEquals(22, new OrderService().countAllOrders());
    }


    @Test
    void findOrdersOfUser() throws DatabaseException {
        assertFalse(new OrderService().findOrdersOfUser(2,1,8).isEmpty());
    }

    @Test
    void countAllOrdersOfUser() throws DatabaseException {
        User user = new UserService().findUserByLogin("User");
        assertEquals(22, new OrderService().countAllOrdersOfUser(user.getId()));
    }

    @Test
    void findItemsOfOrder() throws DatabaseException {
        OrderService orderService = new OrderService();
        Order order = orderService.findOrderById(1,"");
        assertFalse(orderService.findItemsOfOrder(order.getId(), 1,"").isEmpty());
    }

    @Test
    void countTotalCost() throws DatabaseException {
        OrderService orderService = new OrderService();
        Order order = orderService.findOrderById(1,"");
        assertEquals(0, orderService.countTotalCost(order.getId()).compareTo(new BigDecimal(1420)));
    }

    @Test
    void countAllItemsOfOrder() throws DatabaseException {
        Order order = new Order();
        order.setId(1);
        assertEquals(4, new OrderService().countAllItemsOfOrder(order.getId()));
    }

    @Test
    void findOrderById() throws DatabaseException {
        Order order = new OrderService().findOrderById(1,"");
        assertEquals(2,order.getUserId());
    }

}