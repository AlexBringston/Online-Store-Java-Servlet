package com.store.controller.commands;

import com.store.model.entity.OrderItem;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

public class LogoutCommand implements Command{

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession(false);
        if (session.getAttribute("cart") != null) {
            System.out.println(CommandUtils.serializeCart((List<OrderItem>) session.getAttribute("cart")));
            Cookie cookie = new Cookie("cart", CommandUtils.serializeCart((List<OrderItem>) session.getAttribute("cart")));
            response.addCookie(cookie);
        }

        if (session != null) {
            session.invalidate();
        }
        return "redirect:/products";
    }
}
