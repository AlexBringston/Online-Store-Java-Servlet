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

public class ManageOrdersCommand implements Command {

    private OrderService orderService;

    private static final Logger log = Logger.getLogger(ManageOrdersCommand.class);

    public ManageOrdersCommand(OrderService orderService) {
        this.orderService = orderService;
    }

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
