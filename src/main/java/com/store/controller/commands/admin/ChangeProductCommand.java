package com.store.controller.commands.admin;

import com.store.controller.commands.Command;
import com.store.controller.commands.CommandUtils;
import com.store.model.entity.Product;
import com.store.model.exception.DatabaseException;
import com.store.model.service.ProductService;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;

/**
 * Change product command
 * This command implements functionality of changing existing product in catalog.
 * It shows the page with all input forms or, if action equals 'add', which is called when you press 'add product'
 * button, calls product service to add info about this product to database.
 *
 * @author Alexander Mulyk
 * @since 2021-06-14
 */
public class ChangeProductCommand implements Command {

    /**
     * Local variable to use product service in command
     */
    private final ProductService productService;

    /**
     * Logger instance to control proper work
     */
    private static final Logger log = Logger.getLogger(ChangeProductCommand.class);

    /**
     * Constructor, which initializes productService variable
     * @param productService - product service instance
     */
    public ChangeProductCommand(ProductService productService) {
        this.productService = productService;
    }

    /**
     * Implementation of execute command of Command interface. Depending on action, returns either page path, on which
     * you can change product, or performs product service command to change this product and returns redirect path to
     * manage products page.
     * @param request HttpServletRequest instance
     * @param response HttpServletResponse instance
     * @return path to the page
     * @throws DatabaseException if service methods get errors
     */
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws DatabaseException {
        log.info("Change product command started");
        String action = request.getParameter("action");
        String forward = null;
        if (action == null || action.equalsIgnoreCase("open")) {
            forward = setupProductData(request);
        } else {
            if (action.equalsIgnoreCase("change")) {
                forward = changeProductData(request);
            }
        }
        log.info("Change product command finished");
        return forward;
    }

    /**
     * Method to find a product by id, using service method, and send instance to jsp page to display all current
     * info on it.
     * @param request HttpServletRequest instance
     * @return redirect path to admin change product page
     * @throws DatabaseException if service method gets some error
     */
    private String setupProductData(HttpServletRequest request) throws DatabaseException {
        int productId = 0;
        try {
            productId = Integer.parseInt(request.getParameter("id"));
        } catch (NumberFormatException e) {
            String errorMessage = "There is no product with this id";
            request.setAttribute("errorMessage", errorMessage);
            return "/WEB-INF/error.jsp";
        }
        String locale = CommandUtils.checkForLocale(request);
        Product product = productService.getProductById(productId, locale);

        request.setAttribute("product", product);
        return "/WEB-INF/admin/changeProduct.jsp";
    }
    /**
     * Method to perform change in database and change the corresponding product in the products table. It collects
     * all data sent from the html form and calls a service method to change product info.
     * @param request HttpServletRequest instance
     * @return redirect path to admin manage products page
     * @throws DatabaseException if service method gets some error
     */
    private String changeProductData(HttpServletRequest request) throws DatabaseException {
        int id = Integer.parseInt(request.getParameter("id"));
        String name = request.getParameter("name");
        String nameUK = request.getParameter("nameUK");
        String imageLink = request.getParameter("imageLink");
        BigDecimal price = new BigDecimal(request.getParameter("price"));
        String[] category = request.getParameter("category").split("-");
        String[] size = request.getParameter("size").split("-");
        String[] color = request.getParameter("color").split("-");
        Product product = new Product(id, name, nameUK, imageLink, price, category[0], size[0], color[0]);
        log.trace(product);
        productService.updateProduct(product);
        return "redirect:/manageProducts";
    }
}
