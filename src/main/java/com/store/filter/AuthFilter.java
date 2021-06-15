package com.store.filter;

import com.store.controller.commands.CommandUtils;
import com.store.model.entity.Role;
import com.store.model.entity.User;
import com.store.model.exception.DatabaseException;
import com.store.model.service.UserService;
import org.apache.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Auth filter
 * This filter is responsible for setting default user status and, for logged user, checking if user is blocked to
 * restrict access to the website.
 *
 * @author Alexander Mulyk
 * @since 2021-06-14
 */
public class AuthFilter implements Filter {

    /**
     * Logger instance to control proper work
     */
    private static final Logger log = Logger.getLogger(AuthFilter.class);

    /**
     * Filter method which checks user role and either gives them access and goes by chain, or redirects to error
     * page if user role is not admin
     * @param request ServletRequest instance
     * @param response ServletResponse instance
     * @param chain filter chain
     * @throws IOException if cannot go further by filter chain
     * @throws ServletException if chain cannot work as programmed
     */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        final HttpServletRequest req = (HttpServletRequest) request;
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("user");

        if (session.getAttribute("userRole") == null) {
            session.setAttribute("userRole", Role.GUEST);
        }
        String locale = CommandUtils.checkForLocale(req);
        if (user != null) {
            try {
                user = new UserService().findUserById(user.getId(), locale);
            } catch (DatabaseException e) {
                request.setAttribute("errorMessage",e.getMessage());
                req.getRequestDispatcher("/WEB-INF/error.jsp").forward(request,response);
            }

            log.trace(user.getRole().equals(Role.CLIENT) && user.getStatus().equals("Blocked"));
            if (user.getRole().equals(Role.CLIENT) && user.getStatus().equals("Blocked")) {
                String errorMessage = "This user is blocked!!";
                request.setAttribute("errorMessage", errorMessage);
                req.getRequestDispatcher("/WEB-INF/error.jsp").forward(request,response);
            } else {
                chain.doFilter(request, response);
            }
        }  else {
            chain.doFilter(request, response);
        }
    }
}
