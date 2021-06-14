package com.store.controller.commands.admin;

import com.store.controller.commands.Command;
import com.store.model.entity.Product;
import com.store.model.exception.DatabaseException;
import com.store.model.service.ProductService;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;

/**
 * Add product command
 * This command implements functionality of adding product to catalog.
 * It shows the page with all input forms or, if action equals 'add', which is called when you press 'add product'
 * button, calls product service to add info about this product to database.
 *
 * @author Alexander Mulyk
 * @since 2021-06-14
 */
public class AddProductCommand implements Command {

    /**
     * Local variable to use product service in command
     */
    private final ProductService productService;

    /**
     * Logger instance to control proper work
     */
    private static final Logger log = Logger.getLogger(AddProductCommand.class);

    /**
     * Constructor, which initializes productService variable
     * @param productService - product service instance
     */
    public AddProductCommand(ProductService productService) {
        this.productService = productService;
    }

    /**
     * Implementation of execute command of Command interface. Depending on action, returns either page path, on which
     * you can add product, or performs product service command to add this product and returns redirect path to
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
            forward = "/WEB-INF/admin/addProduct.jsp";
        } else {
            if (action.equalsIgnoreCase("add")) {
                forward = addProduct(request);
            }
        }
        log.info("Change product command finished");
        return forward;
    }

    /**
     * Method to perform change in database and add new product to the corresponding table
     * @param request HttpServletRequest instance
     * @return redirect path to admin manage products page
     * @throws DatabaseException if service method gets some error
     */
    private String addProduct(HttpServletRequest request) throws DatabaseException {
        String name = request.getParameter("name");
        String nameUK = request.getParameter("nameUK");
        String imageLink = request.getParameter("imageLink");
        BigDecimal price = new BigDecimal(request.getParameter("price"));
        String[] category = request.getParameter("category").split("-");
        String[] size = request.getParameter("size").split("-");
        String[] color = request.getParameter("color").split("-");
        Product product = new Product(name,nameUK, imageLink, price, category[0], size[0], color[0]);

        productService.addProduct(product);
        return "redirect:/manageProducts";
    }
}
