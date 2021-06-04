package com.store.controller.commands;



import com.store.model.entity.Role;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class AuthorizationCommand implements Command{

    private static final Logger log = Logger.getLogger(LoginCommand.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        Role userRole = (Role) session.getAttribute("userRole");
        if (userRole.equals(Role.ADMIN)) {
            return "redirect:/app/admin";
        }
        else if (userRole.equals(Role.CLIENT)) {
            return "redirect:/app/user";
        } else {
            return "/login.jsp";
        }
    }
}
