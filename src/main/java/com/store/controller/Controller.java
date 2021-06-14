package com.store.controller;

import com.store.controller.commands.*;
import com.store.controller.commands.admin.*;
import com.store.controller.commands.products.ProductListCommand;
import com.store.controller.commands.products.ProductsByCategoryCommand;
import com.store.controller.commands.products.ProductsByColorCommand;
import com.store.controller.commands.products.ProductsBySizeCommand;
import com.store.model.exception.DatabaseException;
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

/**
 * Main controller
 * This controller initializes all commands which are user to navigate through site and implements methods of get or
 * post methods.
 *
 * @author Alexander Mulyk
 * @since 2021-06-14
 */
public class Controller extends HttpServlet {
    /**
     * Variable to keep class serializable
     */
    private static final long serialVersionUID = 2423353715955164816L;

    /**
     * Logger to control proper work of program
     */
    private static final Logger log = Logger.getLogger(Controller.class);

    /**
     * Hashmap which stores all commands names and corresponding classes
     */
    private final Map<String, Command> commands = new HashMap<>();

    /**
     * Method to initialize every command used in program
     */
    public void init(){
        commands.put("products", new ProductListCommand(new ProductService()));
        commands.put("cart", new CartCommand(new ProductService()));
        commands.put("category", new ProductsByCategoryCommand(new ProductService()));
        commands.put("color", new ProductsByColorCommand(new ProductService()));
        commands.put("size", new ProductsBySizeCommand(new ProductService()));
        commands.put("login", new LoginCommand(new UserService()));
        commands.put("register", new RegistrationCommand(new UserService()));
        commands.put("logout", new LogoutCommand());
        commands.put("locale", new LocaleCommand());
        commands.put("authorization", new AuthorizationCommand());
        commands.put("admin", new AdminCommand());
        commands.put("user", new UserCommand(new UserService(), new OrderService()));
        commands.put("order", new OrderCommand(new OrderService()));
        commands.put("showOrder", new ShowOrderCommand(new OrderService()));
        commands.put("manageProducts", new ManageProductsCommand(new ProductService()));
        commands.put("addProduct", new AddProductCommand(new ProductService()));
        commands.put("deleteProduct", new DeleteProductCommand(new ProductService()));
        commands.put("changeProduct", new ChangeProductCommand(new ProductService()));
        commands.put("manageUsers", new ManageUsersCommand(new UserService()));
        commands.put("changeUserStatus", new ChangeUserStatusCommand(new UserService()));
        commands.put("manageOrders", new ManageOrdersCommand(new OrderService()));
        commands.put("success", new SuccessCommand());
        commands.put("makeDeposit", new MakeDepositCommand(new UserService()));
    }

    /**
     * Method to implement get query
     * @param request HttpServletRequest instance
     * @param response HttpServletResponse instance
     * @throws IOException if there was a problem to read data
     * @throws ServletException if there was an error with servlet
     */
    public void doGet(HttpServletRequest request,
                      HttpServletResponse response)
            throws IOException, ServletException {
        process(request, response);
    }

    /**
     * Method to implement post query
     * @param request HttpServletRequest instance
     * @param response HttpServletResponse instance
     * @throws IOException if there was a problem to read data
     * @throws ServletException if there was an error with servlet
     */
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        process(request, response);
    }

    /**
     * Main method of program which controls all ways to navigate through website. It checks path, changes it to get
     * a last variable, checks commands for corresponding way and makes a redirect or forward depending on what
     * command returns.
     * @param request HttpServletRequest instance
     * @param response HttpServletResponse instance
     * @throws IOException if there was a problem to read data
     * @throws ServletException if there was an error with servlet
     */
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
        String page = null;
        try {
            page = command.execute(request, response);
            log.trace("page = " + page);
            if(page.contains("redirect:")){
                response.sendRedirect(page.replace("redirect:", "/app"));
            }else {
                request.getRequestDispatcher(page).forward(request, response);
            }
        } catch (DatabaseException e) {
            request.setAttribute("errorMessage",e.getMessage());
            request.getRequestDispatcher("/WEB-INF/error.jsp").forward(request,response);
        }
    }


}