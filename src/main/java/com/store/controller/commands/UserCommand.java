package com.store.controller.commands;

import com.store.model.dao.Utils;
import com.store.model.entity.Order;
import com.store.model.entity.User;
import com.store.model.exception.DatabaseException;
import com.store.model.service.OrderService;
import com.store.model.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * User command
 * This command implements functionality of displaying user page and their orders.
 *
 * @author Alexander Mulyk
 * @since 2021-06-14
 */
public class UserCommand implements Command {

    /**
     * Local variable to use order service in command
     */
    private final OrderService orderService;

    /**
     * Local variable to use user service in command
     */
    private final UserService userService;

    /**
     * Constructor, which initializes orderService variable
     * @param userService user service instance
     * @param orderService - order service instance
     */
    public UserCommand(UserService userService, OrderService orderService) {
        this.userService = userService;
        this.orderService = orderService;
    }

    /**
     * Implementation of execute command of Command interface. It gets current session and user from this session,
     * locale. It performs a request to the database in order to keep actual user info and block them if their status
     * was changed they they are online and to check their balance without need to login again or changing balance.
     * Simple pagination is also implemented.
     * @param request HttpServletRequest instance
     * @param response HttpServletResponse instance
     * @return path to the page
     * @throws DatabaseException if service methods get errors
     */
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws DatabaseException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        String locale = CommandUtils.checkForLocale(request);
        User tempUser = userService.findUserById(user.getId(),locale);
        user.setBalance(tempUser.getBalance());
        int page = 1;
        if (request.getParameter("page") != null) {
            page = Integer.parseInt(request.getParameter("page"));
        }

        request.setAttribute("currentPage", page);

        int totalCount = orderService.countAllOrdersOfUser(user.getId());

        request.setAttribute("pageCount", CommandUtils.getPageCount(totalCount, Utils.ORDERS_PER_PAGE));

        List<Order> orders = orderService.findOrdersOfUser(user.getId(), page, Utils.ORDERS_PER_PAGE);

        request.setAttribute("orders", orders);

        return "/WEB-INF/user/user.jsp";
    }
}
