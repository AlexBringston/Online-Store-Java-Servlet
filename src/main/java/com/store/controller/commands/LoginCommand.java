package com.store.controller.commands;

import com.store.model.entity.Role;
import com.store.model.entity.User;
import com.store.model.service.OrderService;
import com.store.model.service.UserService;
import org.apache.log4j.Logger;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LoginCommand implements Command {

    private UserService userService;

    public LoginCommand(UserService userService) {
        this.userService = userService;
    }

    private static final Logger log = Logger.getLogger(LoginCommand.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        String login = request.getParameter("login");
        log.trace("Request parameter: login --> " + login);
        request.setAttribute("login", request.getParameter("login"));
        String password = request.getParameter("password");

        String errorMessage;
        String forward = "/WEB-INF/error.jsp";

        if (login == null || password == null || login.isEmpty() || password.isEmpty()) {
            errorMessage = "Login/password cannot be empty";
            request.setAttribute("errorMessage", errorMessage);
            return "/WEB-INF/error.jsp";
        }
        User user = userService.findUserByLogin(login);
        log.trace("Found in DB: user --> " + user);

        if (user == null) {
            errorMessage = "User was not found";
            request.setAttribute("errorMessage", errorMessage);
            return "/WEB-INF/error.jsp";
        } else if (!password.equals(user.getPassword())) {
            errorMessage = "Cannot find user with such login/password";
            request.setAttribute("errorMessage", errorMessage);
            return "/WEB-INF/error.jsp";

        } else {
            if (user.getStatus().equals("BLOCKED")) {
                errorMessage = "This user was blocked";
                request.setAttribute("errorMessage", errorMessage);
                return "/WEB-INF/error.jsp";

            } else if (CommandUtils.checkUserIsLogged(request, user.getLogin())) {
                errorMessage = "This user has already logged in";
                request.setAttribute("errorMessage", errorMessage);
                return "/WEB-INF/error.jsp";
            }
            Role userRole = Role.getRole(user);
            log.trace("userRole --> " + userRole);

            if (userRole == Role.ADMIN) {
                forward = "redirect:/admin";
            }

            if (userRole == Role.CLIENT) {
                forward = "redirect:/user";
                Cookie[] cookies = request.getCookies();
                Cookie cartCookie = CommandUtils.findCookie(cookies, "cart");
                if (cartCookie != null) {
                    session.setAttribute("cart", CommandUtils.deserializeCart(request, cartCookie.getValue()));
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
