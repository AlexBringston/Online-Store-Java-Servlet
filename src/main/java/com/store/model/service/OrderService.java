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

/**
 * Order service which contains methods that create dao instances and use their methods.
 *
 * @author Alexander Mulyk
 * @since 2021-06-14
 */
public class OrderService {
    private static final Logger log = Logger.getLogger(OrderService.class);

    DaoFactory daoFactory = DaoFactory.getInstance();

    /**
     * Method to list all orders in system
     * @return list of orders
     * @throws DatabaseException if error in sql has occurred
     */
    public List<Order> listAllOrders() throws DatabaseException {
        try (OrderDao orderDao = daoFactory.createOrderDao()) {
            return orderDao.findAll().orElseThrow(() -> new DatabaseException("Could not list orders"));
        }
    }

    /**
     * Method to list orders on given page
     * @param pageNumber number of current page
     * @param limit limit of orders per page
     * @return list of orders
     * @throws DatabaseException if error in sql has occurred
     */
    public List<Order> listOrdersPerPage(int pageNumber, int limit) throws DatabaseException {
        try (OrderDao orderDao = daoFactory.createOrderDao()) {
            return orderDao.findOrdersPerPage(pageNumber, limit).orElseThrow(
                    () -> new DatabaseException("Could not list orders on current page"));
        }
    }

    /**
     * Method to count all orders
     * @return total amount of orders
     * @throws DatabaseException if error in sql has occurred
     */
    public int countAllOrders() throws DatabaseException {
        try (OrderDao orderDao = daoFactory.createOrderDao()) {
            return orderDao.countAllOrders();
        }
    }

    /**
     * Method to update an order
     * @param order order instance which contains data to be changed
     * @throws DatabaseException if error in sql has occurred
     */
    public void updateOrder(Order order) throws DatabaseException {
        try (OrderDao orderDao = daoFactory.createOrderDao()) {
            orderDao.update(order);
        }
    }

    /**
     * Method to find orders of user and list them on given page
     * @param userId user id
     * @param pageNumber number of current page
     * @param limit limit of orders per page
     * @return list of orders
     * @throws DatabaseException if error in sql has occurred
     */
    public List<Order> findOrdersOfUser(int userId, int pageNumber, int limit) throws DatabaseException {
        try (OrderDao orderDao = daoFactory.createOrderDao()) {
            return orderDao.findOrdersOfUser(userId, pageNumber, limit).orElseThrow(
                    () -> new DatabaseException("Could not find orders of user"));
        }
    }

    /**
     * Method to count a number of orders of user
     * @param userId user id
     * @return number of user orders
     * @throws DatabaseException if error in sql has occurred
     */
    public int countAllOrdersOfUser(int userId) throws DatabaseException {
        try (OrderDao orderDao = daoFactory.createOrderDao()) {
            return orderDao.countAllOrdersOfUser(userId);
        }
    }

    /**
     * Method to find all items of given order and list them on page
     * @param orderId id of given order
     * @param pageNumber number of current page
     * @param locale locale name
     * @return list of order items
     * @throws DatabaseException if error in sql has occurred
     */
    public List<OrderItem> findItemsOfOrder(int orderId, int pageNumber, String locale) throws DatabaseException {
        try (OrderItemDao orderItemDao = daoFactory.createOrderItemDao()) {
            return orderItemDao.findAllItemsOfOrder(orderId, pageNumber, locale).orElseThrow(
                    () -> new DatabaseException("Could not find content of given order"));
        }
    }

    /**
     * Method to count total cost of given order
     * @param orderId id of order
     * @return BigDecimal value
     * @throws DatabaseException if error in sql has occurred
     */
    public BigDecimal countTotalCost(int orderId) throws DatabaseException {
        try (OrderItemDao orderItemDao = daoFactory.createOrderItemDao()) {
            return orderItemDao.countTotalCost(orderId);
        }
    }

    /**
     * Method to count all items in given order
     * @param orderId id of given order
     * @return number of items in order
     * @throws DatabaseException if error in sql has occurred
     */
    public int countAllItemsOfOrder(int orderId) throws DatabaseException {
        try (OrderItemDao orderItemDao = daoFactory.createOrderItemDao()) {
            return orderItemDao.countAllItemsInOrder(orderId);
        }
    }

    /**
     * Method to find an order by given id and locale
     * @param orderId id of order
     * @param locale locale name
     * @return order instance with given data
     * @throws DatabaseException if error in sql has occurred
     */
    public Order findOrderById(int orderId, String locale) throws DatabaseException {
        try (OrderDao orderDao = daoFactory.createOrderDao()) {
            return orderDao.findById(orderId, locale).orElseThrow(() -> new DatabaseException("Error with order"));
        }
    }

    /**
     * Method to create order in database by given instance
     * @param order Order instance with corresponding data to be added
     * @throws DatabaseException if error in sql has occurred
     */
    public void createOrder(Order order) throws DatabaseException {
        try (OrderDao orderDao = daoFactory.createOrderDao()) {
            orderDao.create(order);
        }
    }

    /**
     * Method to create order item in database by given instance
     * @param orderItem OrderItem intance with given data to be added
     * @throws DatabaseException if error in sql has occurred
     */
    public void createOrderItem(OrderItem orderItem) throws DatabaseException {
        try (OrderItemDao orderItemDao = daoFactory.createOrderItemDao()) {
            orderItemDao.create(orderItem);
        }
    }

    /**
     * Method to create an order and fill it with it's items in database
     * @param order Order instance with corresponding data
     * @param orderItemList OrderItem instance with corresponding data
     * @throws DatabaseException if error in sql has occurred
     */
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
