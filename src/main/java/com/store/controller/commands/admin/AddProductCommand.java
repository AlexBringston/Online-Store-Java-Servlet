package com.store.controller.commands.admin;

import com.store.controller.commands.Command;
import com.store.model.entity.Product;
import com.store.model.service.ProductService;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;

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
