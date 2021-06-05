package com.store.controller.commands.products;

import com.store.controller.commands.Command;
import com.store.controller.commands.CommandUtils;
import com.store.model.dao.Utils;
import com.store.model.entity.Product;
import com.store.model.service.ProductService;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;

public class ProductsByCategoryCommand implements Command {

    private ProductService productService;

    private static final Logger log = Logger.getLogger(ProductListCommand.class);

    public ProductsByCategoryCommand(ProductService productService) {
        this.productService = productService;
    }

    private static final int SUBSTRING_INDEX = "/app/products/category/".length();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        log.trace("request.getRequestURI() --> " + request.getRequestURI());
        String categoryUrl = null;
        try {
            categoryUrl = URLDecoder.decode(request.getRequestURI().substring(SUBSTRING_INDEX), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        log.debug("Category command starts");
        int page = 1;
        if(request.getParameter("page") != null) {
            page = Integer.parseInt(request.getParameter("page"));
        }
        request.setAttribute("currentPage", page);
        log.trace("Set the request attribute: currentPage --> " + page);
        log.trace("categoryUrl --> " + categoryUrl);
        String locale = CommandUtils.checkForLocale(request);
        int totalCount = productService.countProductsByCategory(categoryUrl);
        request.setAttribute("pageCount", CommandUtils.getPageCount(totalCount, Utils.PRODUCTS_PER_PAGE));

        String sort = request.getParameter("parameter");
        String direction = null;
        String orderDirection = request.getParameter("sortDirection");
        if (sort !=null && orderDirection != null) {
            sort = sort.toLowerCase();
            direction = productService.getSortDirection(orderDirection);
        }
        if (sort == null || sort.equals("")) {
            sort = "name";
        }
        if (direction == null || direction.equals("")) {
            direction = "ASC";
        }
        List<Product> products = productService.listProductsPerPageByCategory(page, categoryUrl, sort, direction,
                locale);
        request.setAttribute("products" , products);
        request.setAttribute("sortWay",request.getParameter("parameter"));
        request.setAttribute("sortDirection",request.getParameter("sortDirection"));
        CommandUtils.setAttributes(request, productService);

        request.setAttribute("selectedCategoryUrl", categoryUrl);
        return "/WEB-INF/product-list.jsp";
    }
}
