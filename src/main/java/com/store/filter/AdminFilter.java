package com.store.filter;

import com.store.model.entity.Role;
import org.apache.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Admin filter
 * This filter is responsible for providing access to admin pages only to user with admin role.
 *
 * @author Alexander Mulyk
 * @since 2021-06-14
 */
public class AdminFilter implements Filter {

    /**
     * Logger instance to control proper work
     */
    private static final Logger log = Logger.getLogger(AdminFilter.class);

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
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        log.trace("AdminFilter started");
        final HttpServletRequest req = (HttpServletRequest) request;
        HttpSession session = req.getSession();

        Role userRole = (Role) session.getAttribute("userRole");
        log.trace("User role --> " + userRole);
        if (userRole.equals(Role.ADMIN)) {
            log.trace("AdminFilter finished");
            chain.doFilter(request, response);
        } else {
            String errorMessage = "You do not have permission to access the requested resource";
            request.setAttribute("errorMessage", errorMessage);
            log.trace("Set the request attribute: errorMessage --> " + errorMessage);
            log.trace("AdminFilter finished");
            request.getRequestDispatcher("/WEB-INF/error.jsp")
                    .forward(request, response);
        }
    }
}
