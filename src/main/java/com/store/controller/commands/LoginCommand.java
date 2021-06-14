package com.store.controller.commands;

import com.store.model.entity.Role;
import com.store.model.entity.User;
import com.store.model.exception.DatabaseException;
import com.store.model.service.UserService;
import org.apache.log4j.Logger;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Login command
 * This command implements functionality of logging user on website and validating data entered data.
 *
 * @author Alexander Mulyk
 * @since 2021-06-14
 */
public class LoginCommand implements Command {

    /**
     * Local variable to use user service in command
     */
    private final UserService userService;

    /**
     * Constructor, which initializes userService variable
     * @param userService - user service instance
     */
    public LoginCommand(UserService userService) {
        this.userService = userService;
    }

    /**
     * Logger instance to control proper work
     */
    private static final Logger log = Logger.getLogger(LoginCommand.class);

    /**
     * Implementation of execute command of Command interface. It gets login and password from http request and
     * checks them for validity. Then it tries to look for a user with such data and, if success, sets the user and
     * his role in session, after what redirects to corresponding page
     * @param request HttpServletRequest instance
     * @param response HttpServletResponse instance
     * @return path to the page
     * @throws DatabaseException if service methods get errors
     */
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws DatabaseException {
        HttpSession session = request.getSession();
        String login = request.getParameter("login");
        log.trace("Request parameter: login --> " + login);
        request.setAttribute("login", request.getParameter("login"));
        String password = request.getParameter("password");

        String forward = "/WEB-INF/error.jsp";

        if (login == null || password == null || login.isEmpty() || password.isEmpty()) {
            throw new DatabaseException("Login or password cannot be empty");
        }
        User user = userService.findUserByLogin(login);
        log.trace("Found in DB: user --> " + user);

        if (!password.equals(user.getPassword())) {
            throw new DatabaseException("Cannot find user with such login/password");

        } else {
            if (user.getStatus().equals("BLOCKED")) {
                throw new DatabaseException("This user was blocked");

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
