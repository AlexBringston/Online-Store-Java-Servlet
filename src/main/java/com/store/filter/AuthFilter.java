package com.store.filter;

import com.store.controller.commands.LoginCommand;
import com.store.model.entity.Role;
import org.apache.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class AuthFilter implements Filter {
    private static final Logger log = Logger.getLogger(AuthFilter.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request,
                         ServletResponse response,
                         FilterChain filterChain) throws IOException, ServletException {
        log.trace("AuthFilter started");
        final HttpServletRequest req = (HttpServletRequest) request;

        HttpSession session = req.getSession();

        if (session.getAttribute("userRole") == null) {
            session.setAttribute("userRole", Role.GUEST);
        }
        log.trace("AuthFilter finished");
        filterChain.doFilter(request, response);
    }


    @Override
    public void destroy() {

    }
}
