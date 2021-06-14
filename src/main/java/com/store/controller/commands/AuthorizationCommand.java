package com.store.controller.commands;

import com.store.model.entity.Role;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Authorization command
 * This command is user to redirect guest user to login page and to redirect logged user to their corresponding user
 * page.
 *
 * @author Alexander Mulyk
 * @since 2021-06-14
 */
public class AuthorizationCommand implements Command{

    /**
     * Logger instance to control proper work
     */
    private static final Logger log = Logger.getLogger(LoginCommand.class);

    /**
     * Implementation of execute command of Command interface. Depending on user role, which is retrieved from session,
     * returns path to the admin page, user page or login page.
     * @param request HttpServletRequest instance
     * @param response HttpServletResponse instance
     * @return path to the page
     */
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        Role userRole = (Role) session.getAttribute("userRole");
        if (userRole.equals(Role.ADMIN)) {
            return "redirect:/admin";
        }
        else if (userRole.equals(Role.CLIENT)) {
            return "redirect:/user";
        } else {
            return "/login.jsp";
        }
    }
}
