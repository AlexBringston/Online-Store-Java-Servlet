package com.store.controller.commands;

import com.store.controller.Controller;
import com.store.model.entity.Product;
import com.store.model.service.ProductService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class ProductListCommand implements Command{

    private ProductService productService;

    private static final Logger log = LogManager.getLogger(ProductListCommand.class);

    public ProductListCommand(ProductService productService) {
        this.productService = productService;
    }

    @Override
    public String execute(HttpServletRequest request) {
        log.debug("Commands starts");
        //List<Product> products = productService.listAllProducts();
        //request.setAttribute("products" , products);
        //log.trace("Set the request attribute: products --> " + products);
        log.debug("Commands finished");
        return "/WEB-INF/product-list.jsp";
    }
}
