package com.store.model.service;

import com.store.model.dao.DaoFactory;
import com.store.model.dao.OrderDao;
import com.store.model.dao.OrderItemDao;
import com.store.model.dao.ProductDao;
import com.store.model.entity.Order;
import com.store.model.entity.OrderItem;
import com.store.model.entity.Product;
import org.apache.log4j.Logger;

import java.util.List;

public class OrderService {
    private static final Logger log = Logger.getLogger(OrderService.class);

    DaoFactory daoFactory = DaoFactory.getInstance();

    public List<Order> listAllOrders() {
        try (OrderDao dao = daoFactory.createOrderDao()) {
            return dao.findAll();
        }
    }

    public List<Order> findOrdersOfUser(int userId, int pageNumber) {
        try (OrderDao dao = daoFactory.createOrderDao()) {
            return dao.findOrdersOfUser(userId, pageNumber);
        }
    }

    public int countAllOrdersOfUser(int userId) {
        try (OrderDao dao = daoFactory.createOrderDao()) {
            return dao.countAllOrdersOfUser(userId);
        }
    }


    public void createOrder(Order order) {
        try (OrderDao dao = daoFactory.createOrderDao()) {
            dao.create(order);
        }
    }

    public void createOrderItem(OrderItem orderItem) {
        try (OrderItemDao dao = daoFactory.createOrderItemDao()) {
            dao.create(orderItem);
        }
    }
}
