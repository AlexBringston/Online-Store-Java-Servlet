package com.store.filter;

import com.store.model.entity.Role;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class AuthFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request,
                         ServletResponse response,
                         FilterChain filterChain) throws IOException, ServletException {

        final HttpServletRequest req = (HttpServletRequest) request;
        final HttpServletResponse res = (HttpServletResponse) response;

        HttpSession session = req.getSession();

        if (session.getAttribute("userRole") == null) {
            session.setAttribute("userRole",Role.GUEST);
        }
        
        Role userRole = (Role) session.getAttribute("userRole");
        if (accessAllowed(request, userRole)) {
            filterChain.doFilter(request, response);
        } else {
            String errorMessage = "You do not have permission to access the requested resource";
            request.setAttribute("errorMessage", errorMessage);
            //log.trace("Set the request attribute: errorMessage --> " + errorMessage);
            request.getRequestDispatcher("/WEB-INF/error.jsp")
                    .forward(request, response);
        }
    }

    private boolean accessAllowed(ServletRequest request, Role userRole) {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        String path = httpServletRequest.getRequestURI();
        path = path.replaceAll(".*/app/", "");
        HttpSession session = httpServletRequest.getSession(false);
        if (session == null)
            return false;

        if (userRole == null)
            return false;
        return (userRole.equals(Role.ADMIN) && path.contains("admin"))
                || (userRole.equals(Role.CLIENT) && !path.contains("admin"))
                || (userRole.equals(Role.GUEST) && !path.contains("admin"));
    }

    @Override
    public void destroy() {

    }
}
