package com.store.controller.commands;

import com.store.controller.commands.products.ProductListCommand;
import com.store.model.entity.OrderItem;
import com.store.model.entity.User;
import org.apache.log4j.Logger;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashSet;
import java.util.List;

public class LogoutCommand implements Command{

    private static final Logger log = Logger.getLogger(LogoutCommand.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession(false);
        if (session.getAttribute("cart") != null) {
            System.out.println(CommandUtils.serializeCart((List<OrderItem>) session.getAttribute("cart")));
            Cookie cookie = new Cookie("cart", CommandUtils.serializeCart((List<OrderItem>) session.getAttribute("cart")));
            response.addCookie(cookie);
        }
        HashSet<String> loggedUsers = (HashSet<String>) request.getSession().getServletContext()
                .getAttribute("loggedUsers");
        if (loggedUsers != null) {
            loggedUsers.remove(((User) session.getAttribute("user")).getLogin());
        }
        session.invalidate();
        return "redirect:/products";
    }
}
