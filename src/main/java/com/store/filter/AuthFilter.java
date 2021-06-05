package com.store.filter;

import com.store.controller.commands.LoginCommand;
import com.store.model.entity.Role;
import com.store.model.entity.User;
import com.store.model.service.UserService;
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
        final HttpServletRequest req = (HttpServletRequest) request;

        HttpSession session = req.getSession();

        User user = (User) session.getAttribute("user");

        if (session.getAttribute("userRole") == null) {
            session.setAttribute("userRole", Role.GUEST);
        }

        if (user != null) {
            user = new UserService().findUserById(user.getId());
            if (user.getRole().equals(Role.CLIENT) && user.getStatus().equals("BLOCKED")) {
                String errorMessage = "This user is blocked!!";
                request.setAttribute("errorMessage",errorMessage);
                req.getRequestDispatcher("/WEB-INF/error.jsp").forward(request,response);
            } else {
                filterChain.doFilter(request, response);
            }
        }  else {
            filterChain.doFilter(request, response);
        }

    }


    @Override
    public void destroy() {

    }
}
