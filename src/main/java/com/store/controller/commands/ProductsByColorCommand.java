package com.store.controller.commands;

import com.store.model.entity.Category;
import com.store.model.entity.Color;
import com.store.model.entity.Product;
import com.store.model.entity.Size;
import com.store.model.service.ProductService;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Locale;

public class ProductsByColorCommand implements Command{

    private ProductService productService;

    private static final Logger log = Logger.getLogger(ProductListCommand.class);

    public ProductsByColorCommand(ProductService productService) {
        this.productService = productService;
    }

    private static final int SUBSTRING_INDEX = "/app/products/color/".length();

    @Override
    public String execute(HttpServletRequest request) {
        String colorUrl = request.getRequestURI().substring(SUBSTRING_INDEX);
        log.debug("Color command starts");
        int page = 1;
        if(request.getParameter("page") != null) {
            page = Integer.parseInt(request.getParameter("page"));
        }
        request.setAttribute("currentPage", page);
        log.trace("Set the request attribute: currentPage --> " + page);
        log.trace("colorUrl --> " + colorUrl);
        int totalCount = productService.countProductsByColor(colorUrl);
        request.setAttribute("pageCount",ProductListCommand.getPageCount(totalCount,8));

        String sort = request.getParameter("parameter");
        String direction = null;
        String orderDirection = request.getParameter("sortDirection");
        if (sort !=null && orderDirection != null) {
            sort = sort.toLowerCase();
            direction = productService.getSortDirection(orderDirection);
        }
        if (sort == null || sort.equals("")) {
            sort = "id";
        }
        if (direction == null || direction.equals("")) {
            direction = "ASC";
        }
        log.trace("sort ->>>" + sort);
        log.trace("direction ->>>" + direction);
        log.trace("colorUrl ->>>" + colorUrl);
        List<Product> products = productService.listProductsPerPageByColor(page, colorUrl, sort, direction);
        request.setAttribute("sortWay",request.getParameter("parameter"));
        request.setAttribute("sortDirection",request.getParameter("sortDirection"));
        request.setAttribute("products" , products);
        CommandUtils.setAttributes(request, productService);

        request.setAttribute("selectedCategoryUrl", colorUrl);
        log.trace("Set the request attribute: products --> " + products);
        log.debug("Color command finished");
        return "/WEB-INF/product-list.jsp";
    }
}
