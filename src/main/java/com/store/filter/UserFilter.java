package com.store.filter;

import com.store.model.entity.Role;
import org.apache.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * User filter
 * This filter is responsible for providing access to user pages only to logged users with other than client role.
 *
 * @author Alexander Mulyk
 * @since 2021-06-14
 */
public class UserFilter implements Filter {

    /**
     * Logger instance to control proper work
     */
    private static final Logger log = Logger.getLogger(UserFilter.class);

    /**
     * Filter method which checks role of user and either goes further by chain or restricts access and shows the
     * error page.
     * @param request ServletRequest instance
     * @param response ServletResponse instance
     * @param chain filter chain
     * @throws IOException if cannot go further by filter chain
     * @throws ServletException if chain cannot work as programmed
     */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        log.trace("UserFilter started");
        final HttpServletRequest req = (HttpServletRequest) request;
        HttpSession session = req.getSession();


        Role userRole = (Role) session.getAttribute("userRole");
        log.trace("User role --> " + userRole);

        if ((userRole.equals(Role.ADMIN) || userRole.equals(Role.CLIENT)) && req.getRequestURI().contains("logout")
                || userRole.equals(Role.CLIENT)) {
            chain.doFilter(request, response);
        } else {
            String errorMessage = "You do not have permission to access the requested resource";
            request.setAttribute("errorMessage", errorMessage);
            log.trace("Set the request attribute: errorMessage --> " + errorMessage);
            request.getRequestDispatcher("/WEB-INF/error.jsp")
                    .forward(request, response);
        }
        log.trace("UserFilter finished");
    }

}
