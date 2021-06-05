package com.store.controller.commands.admin;

import com.store.controller.commands.Command;
import com.store.model.entity.Product;
import com.store.model.service.ProductService;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;

public class ChangeProductCommand implements Command {

    private ProductService productService;

    private static final Logger log = Logger.getLogger(ChangeProductCommand.class);

    public ChangeProductCommand(ProductService productService) {
        this.productService = productService;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
        log.info("Change product command started");
        String action = request.getParameter("action");
        String forward = null;
        if (action == null || action.equalsIgnoreCase("open")) {
            forward = setupProductData(request, response);
        } else {
            if (action.equalsIgnoreCase("change")) {
                forward = changeProductData(request);
            }
        }
        log.info("Change product command finished");
        return forward;
    }

    private String setupProductData(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int productId = 0;
        try {
            productId = Integer.parseInt(request.getParameter("id"));
        } catch (NumberFormatException e) {
            String errorMessage = "There is no product with this id";
            request.setAttribute("errorMessage", errorMessage);
            return "/WEB-INF/error.jsp";
        }
        Product product = productService.getProductById(productId);

        request.setAttribute("product", product);
        return "/WEB-INF/admin/changeProduct.jsp";
    }

    private String changeProductData(HttpServletRequest request) {
        int id = Integer.parseInt(request.getParameter("id"));
        String name = request.getParameter("name");
        String description = request.getParameter("description");
        String imageLink = request.getParameter("imageLink");
        BigDecimal price = new BigDecimal(request.getParameter("price"));
        String category = request.getParameter("category");
        String size = request.getParameter("size");
        String color = request.getParameter("color");
        Product product = new Product(id, name, description, imageLink, price, category, size, color);

        productService.updateProduct(product);
        return "redirect:/manageProducts";
    }
}
