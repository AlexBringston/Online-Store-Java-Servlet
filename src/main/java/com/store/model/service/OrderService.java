package com.store.model.service;

import com.store.model.dao.DaoFactory;
import com.store.model.dao.OrderDao;
import com.store.model.dao.OrderItemDao;
import com.store.model.entity.Order;
import com.store.model.entity.OrderItem;
import com.store.model.exception.DatabaseException;
import org.apache.log4j.Logger;

import java.math.BigDecimal;
import java.util.List;

public class OrderService {
    private static final Logger log = Logger.getLogger(OrderService.class);

    DaoFactory daoFactory = DaoFactory.getInstance();

    public List<Order> listAllOrders() throws DatabaseException {
        DaoFactory daoFactory = DaoFactory.getInstance();
        OrderDao orderDao = daoFactory.createOrderDao();
        return orderDao.findAll().orElseThrow(() -> new DatabaseException("Could not list orders"));

    }

    public List<Order> listOrdersPerPage(int pageNumber, int limit) throws DatabaseException {
        DaoFactory daoFactory = DaoFactory.getInstance();
        OrderDao orderDao = daoFactory.createOrderDao();
        return orderDao.findOrdersPerPage(pageNumber, limit).orElseThrow(
                () -> new DatabaseException("Could not list orders on current page"));
    }

    public int countAllOrders() throws DatabaseException {
        DaoFactory daoFactory = DaoFactory.getInstance();
        OrderDao orderDao = daoFactory.createOrderDao();
        return orderDao.countAllOrders();

    }

    public void updateOrder(Order order) throws DatabaseException {
        DaoFactory daoFactory = DaoFactory.getInstance();
        OrderDao orderDao = daoFactory.createOrderDao();
        orderDao.update(order);

    }

    public List<Order> findOrdersOfUser(int userId, int pageNumber, int limit) throws DatabaseException {
        DaoFactory daoFactory = DaoFactory.getInstance();
        OrderDao orderDao = daoFactory.createOrderDao();
        return orderDao.findOrdersOfUser(userId, pageNumber, limit).orElseThrow(
                () -> new DatabaseException("Could not find orders of user"));

    }

    public int countAllOrdersOfUser(int userId) throws DatabaseException {
        DaoFactory daoFactory = DaoFactory.getInstance();
        OrderDao orderDao = daoFactory.createOrderDao();
        return orderDao.countAllOrdersOfUser(userId);
    }

    public List<OrderItem> findItemsOfOrder(int orderId, int pageNumber, String locale) throws DatabaseException {
        DaoFactory daoFactory = DaoFactory.getInstance();
        OrderItemDao orderItemDao = daoFactory.createOrderItemDao();
        return orderItemDao.findAllItemsOfOrder(orderId, pageNumber, locale).orElseThrow(
                () -> new DatabaseException("Could not find content of given order"));

    }

    public BigDecimal countTotalCost(int orderId) throws DatabaseException {
        DaoFactory daoFactory = DaoFactory.getInstance();
        OrderItemDao orderItemDao = daoFactory.createOrderItemDao();
        return orderItemDao.countTotalCost(orderId);

    }

    public int countAllItemsOfOrder(int orderId) throws DatabaseException {
        DaoFactory daoFactory = DaoFactory.getInstance();
        OrderItemDao orderItemDao = daoFactory.createOrderItemDao();
        return orderItemDao.countAllItemsInOrder(orderId);

    }

    public Order findOrderById(int orderId, String locale) throws DatabaseException {
        DaoFactory daoFactory = DaoFactory.getInstance();
        OrderDao orderDao = daoFactory.createOrderDao();
        return orderDao.findById(orderId, locale).orElseThrow(() -> new DatabaseException("Error with order"));
    }

    public void createOrder(Order order) throws DatabaseException {
        DaoFactory daoFactory = DaoFactory.getInstance();
        OrderDao orderDao = daoFactory.createOrderDao();
        orderDao.create(order);
    }

    public void createOrderItem(OrderItem orderItem) throws DatabaseException {
        DaoFactory daoFactory = DaoFactory.getInstance();
        OrderItemDao orderItemDao = daoFactory.createOrderItemDao();
        orderItemDao.create(orderItem);
    }

    public void createAndFillOrder(Order order, List<OrderItem> orderItemList) throws DatabaseException {
        try (OrderDao orderDao = daoFactory.createOrderDao();
             OrderItemDao orderItemDao = daoFactory.createOrderItemDao()) {
            orderDao.create(order);
            for (OrderItem orderItem : orderItemList) {
                orderItem.setOrderId(order.getId());
                orderItemDao.create(orderItem);
            }
        }
    }
}
