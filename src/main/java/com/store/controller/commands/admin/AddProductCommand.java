package com.store.controller.commands.admin;

import com.store.controller.commands.Command;
import com.store.model.entity.Product;
import com.store.model.service.ProductService;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AddProductCommand implements Command {

    private ProductService productService;

    private static final Logger log = Logger.getLogger(AddProductCommand.class);

    public AddProductCommand(ProductService productService) {
        this.productService = productService;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
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

    private String addProduct(HttpServletRequest request) {
        String name = request.getParameter("name");
        String description = request.getParameter("description");
        String imageLink = request.getParameter("imageLink");
        Integer price = Integer.parseInt(request.getParameter("price"));
        String category = request.getParameter("category");
        String size = request.getParameter("size");
        String color = request.getParameter("color");
        Product product = new Product(name, description, imageLink, price, category, size, color);

        productService.addProduct(product);
        return "redirect:/manageProducts";
    }
}
