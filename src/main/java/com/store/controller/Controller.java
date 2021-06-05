package com.store.controller;

import com.store.controller.commands.*;
import com.store.controller.commands.admin.*;
import com.store.controller.commands.products.ProductListCommand;
import com.store.controller.commands.products.ProductsByCategoryCommand;
import com.store.controller.commands.products.ProductsByColorCommand;
import com.store.controller.commands.products.ProductsBySizeCommand;
import com.store.model.service.OrderService;
import com.store.model.service.ProductService;
import com.store.model.service.UserService;
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
        commands.put("cart", new CartCommand(new ProductService()));
        commands.put("category", new ProductsByCategoryCommand(new ProductService()));
        commands.put("color", new ProductsByColorCommand(new ProductService()));
        commands.put("size", new ProductsBySizeCommand(new ProductService()));
        commands.put("login", new LoginCommand(new UserService()));
        commands.put("logout", new LogoutCommand());
        commands.put("locale", new LocaleCommand());
        commands.put("authorization", new AuthorizationCommand());
        commands.put("admin", new AdminCommand());
        commands.put("user", new UserCommand(new OrderService()));
        commands.put("order", new OrderCommand(new OrderService()));
        commands.put("showOrder", new ShowOrderCommand(new OrderService()));
        commands.put("manageProducts", new ManageProductsCommand(new ProductService()));
        commands.put("addProduct", new AddProductCommand(new ProductService()));
        commands.put("deleteProduct", new DeleteProductCommand(new ProductService()));
        commands.put("changeProduct", new ChangeProductCommand(new ProductService()));
        commands.put("manageUsers", new ManageUsersCommand(new UserService()));
        commands.put("changeUserStatus", new ChangeUserStatusCommand(new UserService()));
        commands.put("manageOrders", new ManageOrdersCommand(new OrderService()));
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
            parameter = parameter.substring(0,parameter.indexOf("/"));
            command = commands.get(parameter);
        } else {
            command = commands.getOrDefault(path, (r,re) -> "/WEB-INF/product-list.jsp");
        }
        String page = command.execute(request, response);
        if(page.contains("redirect:")){
            response.sendRedirect(page.replace("redirect:", "/app"));
        }else {
            request.getRequestDispatcher(page).forward(request, response);
        }
    }


}