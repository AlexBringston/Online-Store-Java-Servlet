package com.store.controller.commands;

import com.store.model.dao.DaoFactory;
import com.store.model.entity.Role;
import com.store.model.entity.User;
import com.store.model.service.OrderService;
import org.apache.log4j.Logger;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LoginCommand implements Command {

    private static final Logger log = Logger.getLogger(LoginCommand.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        String login = request.getParameter("login");
        log.trace("Request parameter: login --> " + login);

        String password = request.getParameter("password");

        String errorMessage;
        String forward = "redirect:/WEB-INF/error.jsp";

        if (login == null || password == null || login.isEmpty() || password.isEmpty()) {
            errorMessage = "Login/password cannot be empty";
            request.setAttribute("errorMessage", errorMessage);
            log.error("errorMessage --> " + errorMessage);
            return forward;
        }
        User user = DaoFactory.getInstance().createUserDao().findUserByLogin(login);
        log.trace("Found in DB: user --> " + user);

        if (user == null || !password.equals(user.getPassword())) {
            errorMessage = "Cannot find user with such login/password";
            request.setAttribute("errorMessage", errorMessage);
            log.error("errorMessage --> " + errorMessage);
            return forward;
        } else {
            Role userRole = Role.getRole(user);
            log.trace("userRole --> " + userRole);

            if (userRole == Role.ADMIN) {
                forward = "redirect:/admin";
            }

            if (userRole == Role.CLIENT) {
                forward = "redirect:/user";
                Cookie[] cookies = request.getCookies();
                Cookie cartCookie = CommandUtils.findCookie(cookies,"cart");
                log.info("cookie -> " + cartCookie.toString());
                if (cartCookie != null) {
                    session.setAttribute("cart", CommandUtils.deserializeCart(cartCookie.getValue()));
                }
            }

            session.setAttribute("user", user);
            log.trace("Set the session attribute: user --> " + user);

            session.setAttribute("userRole", userRole);
            log.trace("Set the session attribute: userRole --> " + userRole);

            log.info("User " + user + " logged as " + userRole.toString().toLowerCase());

        }

        log.debug("Command finished");
        return forward;
    }
}
