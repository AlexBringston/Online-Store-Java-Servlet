package com.store.controller.commands.admin;

import com.store.controller.commands.Command;
import com.store.controller.commands.CommandUtils;
import com.store.controller.commands.products.ProductListCommand;
import com.store.model.entity.Product;
import com.store.model.exception.DatabaseException;
import com.store.model.service.ProductService;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Delete product command
 * This command implements functionality of deleting product from catalog.
 * Product is deleted from table 'products' and is added to the table 'deleted_products'.
 *
 * @author Alexander Mulyk
 * @since 2021-06-14
 */
public class DeleteProductCommand implements Command {

    /**
     * Local variable to use product service in command
     */
    private final ProductService productService;

    /**
     * Logger instance to control proper work
     */
    private static final Logger log = Logger.getLogger(ProductListCommand.class);

    /**
     * Constructor, which initializes productService variable
     * @param productService - product service instance
     */
    public DeleteProductCommand(ProductService productService) {
        this.productService = productService;
    }

    /**
     * Implementation of execute command of Command interface. Searches for the product by given id and performs the
     * action of deleting it. After that returns path to the manage products page.
     * @param request HttpServletRequest instance
     * @param response HttpServletResponse instance
     * @return path to the page
     * @throws DatabaseException if service methods get errors
     */
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, DatabaseException {
        int productId = 0;
        try {
            productId = Integer.parseInt(request.getParameter("id"));
        } catch (NumberFormatException e) {
            String errorMessage = "There is no product with this id";
            request.setAttribute("errorMessage",errorMessage);
            return "/WEB-INF/error.jsp";
        }
        String locale = CommandUtils.checkForLocale(request);
        Product product = productService.getProductById(productId, locale);

        productService.addDeletedProduct(product);
        productService.deleteProduct(productId);

        return "redirect:/manageProducts";
    }
}
