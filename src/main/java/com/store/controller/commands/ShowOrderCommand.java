package com.store.controller.commands;

import com.store.model.dao.Utils;
import com.store.model.entity.Order;
import com.store.model.entity.OrderItem;
import com.store.model.entity.User;
import com.store.model.exception.DatabaseException;
import com.store.model.service.OrderService;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.List;

/**
 * Show order command
 * This command implements functionality of showing chosen order.
 * It shows the page with info about every order item and total price for this order.
 *
 * @author Alexander Mulyk
 * @since 2021-06-14
 */
public class ShowOrderCommand implements Command{

    /**
     * Local variable to use order service in command
     */
    private final OrderService orderService;

    /**
     * Logger instance to control proper work
     */
    private static final Logger log = Logger.getLogger(ShowOrderCommand.class);

    /**
     * Constructor, which initializes orderService variable
     * @param orderService - order service instance
     */
    public ShowOrderCommand(OrderService orderService) {
        this.orderService = orderService;
    }

    /**
     * Implementation of execute command of Command interface. It gets current session and user from this session,
     * order id from request. Looks for order with order service and displays it's items corresponding to limit of
     * items per page.
     * @param request HttpServletRequest instance
     * @param response HttpServletResponse instance
     * @return path to the page
     * @throws DatabaseException if service methods get errors
     */
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws DatabaseException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

        int orderId = Integer.parseInt(request.getParameter("id"));
        request.setAttribute("orderId", request.getParameter("id"));
        String locale = CommandUtils.checkForLocale(request);
        Order order = orderService.findOrderById(orderId, locale);
        if (order.getUserId() != user.getId()) {
            throw new DatabaseException("You tried to see orders of different user");
        } else {
            int page = 1;
            if (request.getParameter("page") != null) {
                page = Integer.parseInt(request.getParameter("page"));
            }
            request.setAttribute("currentPage", page);
            BigDecimal totalCostOfOrder = orderService.countTotalCost(orderId);
            request.setAttribute("totalCost", totalCostOfOrder);
            int totalCount = orderService.countAllItemsOfOrder(orderId);
            request.setAttribute("pageCount", CommandUtils.getPageCount(totalCount, Utils.ORDER_ITEMS_PER_PAGE));
            List<OrderItem> orderItems = orderService.findItemsOfOrder(orderId, page, locale);
            request.setAttribute("orderItems", orderItems);
            return "/WEB-INF/user/order-content.jsp";
        }
    }
}
