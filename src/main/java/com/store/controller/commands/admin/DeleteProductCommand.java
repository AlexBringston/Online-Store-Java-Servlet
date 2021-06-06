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

public class DeleteProductCommand implements Command {

    private ProductService productService;

    private static final Logger log = Logger.getLogger(ProductListCommand.class);

    public DeleteProductCommand(ProductService productService) {
        this.productService = productService;
    }

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
