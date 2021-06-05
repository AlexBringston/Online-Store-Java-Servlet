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

public class ProductsByColorCommand implements Command {

    private ProductService productService;

    private static final Logger log = Logger.getLogger(ProductListCommand.class);

    public ProductsByColorCommand(ProductService productService) {
        this.productService = productService;
    }

    private static final int SUBSTRING_INDEX = "/app/products/color/".length();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String colorUrl = null;
        try {
            colorUrl = URLDecoder.decode(request.getRequestURI().substring(SUBSTRING_INDEX), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        log.debug("Color command starts");
        int page = 1;
        if(request.getParameter("page") != null) {
            page = Integer.parseInt(request.getParameter("page"));
        }
        request.setAttribute("currentPage", page);
        log.trace("Set the request attribute: currentPage --> " + page);
        log.trace("colorUrl --> " + colorUrl);
        int totalCount = productService.countProductsByColor(colorUrl);
        request.setAttribute("pageCount", CommandUtils.getPageCount(totalCount, Utils.PRODUCTS_PER_PAGE));

        String sort = request.getParameter("parameter");
        String direction = null;
        String orderDirection = request.getParameter("sortDirection");
        log.trace(orderDirection);
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
        String locale = CommandUtils.checkForLocale(request);
        List<Product> products = productService.listProductsPerPageByColor(page, colorUrl, sort, direction, locale);
        request.setAttribute("sortWay",request.getParameter("parameter"));
        request.setAttribute("sortDirection",request.getParameter("sortDirection"));
        request.setAttribute("products" , products);
        CommandUtils.setAttributes(request, productService);

        request.setAttribute("selectedCategoryUrl", colorUrl);
        return "/WEB-INF/product-list.jsp";
    }
}
