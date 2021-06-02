package com.store.controller.commands;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class LogoutCommand implements Command{

    @Override
    public String execute(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        System.out.println(session.getAttribute("shoppingCart"));
        if (session != null) {
            session.invalidate();
        }
        return "redirect:/products";
    }
}
