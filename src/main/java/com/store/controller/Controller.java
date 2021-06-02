package com.store.controller;

import com.store.controller.commands.*;
import com.store.model.service.ProductService;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Controller extends HttpServlet {

    private static final long serialVersionUID = 2423353715955164816L;

    private static final Logger log = Logger.getLogger(Controller.class);

    Map<String, Command> commands = new HashMap<>();
    public void init(){
        commands.put("products", new ProductListCommand(new ProductService()));
        commands.put("category", new ProductsByCategoryCommand(new ProductService()));
        commands.put("color", new ProductsByColorCommand(new ProductService()));
        commands.put("size", new ProductsBySizeCommand(new ProductService()));
        commands.put("login", new LoginCommand());
        commands.put("logout", new LogoutCommand());
        commands.put("authorization", new AuthorizationCommand());
        commands.put("admin", new AdminCommand());
        commands.put("user", new UserCommand());
    }

    public void doGet(HttpServletRequest request,
                      HttpServletResponse response)
            throws IOException, ServletException {
        process(request, response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        process(request, response);
    }

    private void process(HttpServletRequest request,
                         HttpServletResponse response) throws IOException, ServletException {
        log.debug("Controller starts");
        log.debug("------------------------------------------------------");
        String path = request.getRequestURI();
        path = path.replaceAll(".*/app/" , "");
        Command command;
        log.trace("path --> " + path);
        if (path.contains("products/")) {
            String parameter = path.replace("products/", "");
            log.trace("parameter1 --> " + parameter);
            parameter = parameter.substring(0,parameter.indexOf("/"));
            log.trace("parameter2 --> " + parameter);
            command = commands.get(parameter);
        } else {
            command = commands.getOrDefault(path, (r) -> "/WEB-INF/product-list.jsp");
        }
        //Command command = commands.getOrDefault(path , (r)->"/WEB-INF/product-list.jsp");
        log.trace("Forward address --> " + command);
        String page = command.execute(request);
        log.trace("Forward address --> " + path);
        if(page.contains("redirect:")){
            response.sendRedirect(page.replace("redirect:", "/app"));
        }else {
            request.getRequestDispatcher(page).forward(request, response);
        }
    }


}