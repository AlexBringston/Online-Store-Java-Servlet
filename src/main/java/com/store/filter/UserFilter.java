package com.store.filter;

import com.store.model.entity.Role;
import com.store.model.entity.User;
import org.apache.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class UserFilter implements Filter {

    private static final Logger log = Logger.getLogger(UserFilter.class);
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        log.trace("UserFilter started");
        final HttpServletRequest req = (HttpServletRequest) request;
        final HttpServletResponse res = (HttpServletResponse) response;
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

    @Override
    public void destroy() {

    }
}
