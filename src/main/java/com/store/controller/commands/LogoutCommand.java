package com.store.controller.commands;

import com.store.model.entity.OrderItem;
import org.apache.log4j.Logger;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * Logout command
 * This command implements functionality of logging out from website.
 *
 * @author Alexander Mulyk
 * @since 2021-06-14
 */
public class LogoutCommand implements Command{

    /**
     * Logger instance to control proper work
     */
    private static final Logger log = Logger.getLogger(LogoutCommand.class);

    /**
     * Implementation of execute command of Command interface. It gets current session, serializes current cart in a
     * cookie and invalidates session.
     * @param request HttpServletRequest instance
     * @param response HttpServletResponse instance
     * @return path to the page
     */
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession(false);
        if (session.getAttribute("cart") != null) {
            System.out.println(CommandUtils.serializeCart((List<OrderItem>) session.getAttribute("cart")));
            Cookie cookie = new Cookie("cart", CommandUtils.serializeCart((List<OrderItem>) session.getAttribute("cart")));
            response.addCookie(cookie);
        }

        session.invalidate();
        return "redirect:/products";
    }
}
