package com.store.controller.commands;

import com.store.model.entity.Product;
import com.store.model.service.ProductService;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class ProductListCommand implements Command{

    private ProductService productService;

    private static final Logger log = Logger.getLogger(ProductListCommand.class);

    public ProductListCommand(ProductService productService) {
        this.productService = productService;
    }

    @Override
    public String execute(HttpServletRequest request) {
        log.debug("Commands starts");
        int page = 1;
        if(request.getParameter("page") != null) {
            page = Integer.parseInt(request.getParameter("page"));
        }
        log.trace("page ->>>" + page);


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
        log.trace("sort -> " + sort);
        log.trace("direction -> "+ direction);
        request.setAttribute("sortWay",request.getParameter("parameter"));
        request.setAttribute("sortDirection",request.getParameter("sortDirection"));
        request.setAttribute("currentPage", page);
        log.trace("Set the request attribute: currentPage --> " + page);
        int totalCount = productService.countAllProducts();
        request.setAttribute("pageCount",getPageCount(totalCount,8));

        List<Product> products = productService.listProductsPerPage(page, sort, direction);

        request.setAttribute("products" , products);

        CommandUtils.setAttributes(request, productService);

        log.trace("Set the request attribute: products --> " + products);
        log.debug("Commands finished");
        return "/WEB-INF/product-list.jsp";
    }

    public static int getPageCount(int total, int numberPerPage) {
        int pageCount = total / numberPerPage;
        if(pageCount * numberPerPage != total) {
            pageCount++;
        }
        return pageCount;
    }

}
