package com.store.filter;

import com.store.controller.commands.LoginCommand;
import com.store.model.entity.Role;
import org.apache.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class AdminFilter implements Filter {

    private static final Logger log = Logger.getLogger(AdminFilter.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        log.trace("AdminFilter started");
        final HttpServletRequest req = (HttpServletRequest) request;
        final HttpServletResponse res = (HttpServletResponse) response;
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

    @Override
    public void destroy() {

    }
}
