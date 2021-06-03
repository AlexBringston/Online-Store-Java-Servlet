package com.store.controller.commands;

import com.store.model.dao.Utils;
import com.store.model.entity.Order;
import com.store.model.entity.OrderItem;
import com.store.model.entity.User;
import com.store.model.service.OrderService;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

public class ShowOrderCommand implements Command{

    private OrderService orderService;

    private static final Logger log = Logger.getLogger(ShowOrderCommand.class);

    public ShowOrderCommand(OrderService orderService) {
        this.orderService = orderService;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

        int orderId = Integer.parseInt(request.getParameter("id"));
        request.setAttribute("orderId", request.getParameter("id"));

        Order order = orderService.findOrderById(orderId);
        if (order.getUserId() != user.getId()) {
            String errorMessage = "You tried to see orders of different user";
            request.setAttribute("errorMessage", errorMessage);
            log.error("errorMessage --> " + errorMessage);
            return "redirect:/WEB-INF/error.jsp";
        } else {
            int page = 1;
            if (request.getParameter("page") != null) {
                page = Integer.parseInt(request.getParameter("page"));
            }

            request.setAttribute("currentPage", page);
            int totalCostOfOrder = orderService.countTotalCost(orderId);
            request.setAttribute("totalCost", totalCostOfOrder);
            int totalCount = orderService.countAllItemsOfOrder(orderId);
            request.setAttribute("pageCount", CommandUtils.getPageCount(totalCount, Utils.ORDER_ITEMS_PER_PAGE));
            List<OrderItem> orderItems = orderService.findItemsOfOrder(orderId, page);
            request.setAttribute("orderItems", orderItems);
            return "/WEB-INF/user/order-content.jsp";
        }
    }
}
