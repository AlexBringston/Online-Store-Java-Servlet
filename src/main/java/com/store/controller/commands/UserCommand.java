package com.store.controller.commands;

import com.store.model.dao.Utils;
import com.store.model.entity.Order;
import com.store.model.entity.User;
import com.store.model.service.OrderService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

public class UserCommand implements Command {

    private OrderService orderService;

    public UserCommand(OrderService orderService) {
        this.orderService = orderService;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        int page = 1;
        if (request.getParameter("page") != null) {
            page = Integer.parseInt(request.getParameter("page"));
        }

        request.setAttribute("currentPage", page);

        int totalCount = orderService.countAllOrdersOfUser(user.getId());

        request.setAttribute("pageCount", CommandUtils.getPageCount(totalCount, Utils.ORDERS_PER_PAGE));

        List<Order> orders = orderService.findOrdersOfUser(user.getId(), page);

        request.setAttribute("orders", orders);

        return "/WEB-INF/user/user.jsp";
    }
}
