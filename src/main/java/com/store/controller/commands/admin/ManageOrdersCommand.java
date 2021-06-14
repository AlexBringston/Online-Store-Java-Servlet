package com.store.controller.commands.admin;

import com.store.controller.commands.Command;
import com.store.controller.commands.CommandUtils;
import com.store.model.dao.Utils;
import com.store.model.entity.Order;
import com.store.model.exception.DatabaseException;
import com.store.model.service.OrderService;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Manage orders command
 * This command implements functionality of showing all orders.
 * Also there is part to give admin an opportunity to change order status.
 *
 * @author Alexander Mulyk
 * @since 2021-06-14
 */
public class ManageOrdersCommand implements Command {

    /**
     * Local variable to use order service in command
     */
    private final OrderService orderService;

    /**
     * Logger instance to control proper work
     */
    private static final Logger log = Logger.getLogger(ManageOrdersCommand.class);

    /**
     * Constructor, which initializes orderService variable
     * @param orderService - order service instance
     */
    public ManageOrdersCommand(OrderService orderService) {
        this.orderService = orderService;
    }

    /**
     * Implementation of execute command of Command interface. Searches for the order id and status, and if they
     * exist, looks for the corresponding order in database and updates it's status. Otherwise gets the page number
     * and displays all orders for current page.
     * @param request HttpServletRequest instance
     * @param response HttpServletResponse instance
     * @return path to the page
     * @throws DatabaseException if service methods get errors
     */
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws DatabaseException {
        String status = request.getParameter("status");
        String idValue = request.getParameter("id");
        int id = 0;
        if (idValue != null) {
            id = Integer.parseInt(request.getParameter("id"));
        }

        if (status != null) {
            String locale = CommandUtils.checkForLocale(request);
            Order order = orderService.findOrderById(id, locale);
            order.setStatus(status);
            orderService.updateOrder(order);
        }
        int page = 1;
        if (request.getParameter("page") != null) {
            page = Integer.parseInt(request.getParameter("page"));
        }
        request.setAttribute("currentPage", page);
        int totalCount = orderService.countAllOrders();
        request.setAttribute("pageCount", CommandUtils.getPageCount(totalCount, Utils.ORDERS_PER_PAGE));

        List<Order> orders = orderService.listOrdersPerPage(page, Utils.ORDERS_PER_PAGE);
        request.setAttribute("orders", orders);
        return "/WEB-INF/admin/orders.jsp";
    }
}
