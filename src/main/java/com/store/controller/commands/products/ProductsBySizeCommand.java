package com.store.controller.commands.products;

import com.store.controller.commands.Command;
import com.store.controller.commands.CommandUtils;
import com.store.model.entity.Product;
import com.store.model.exception.DatabaseException;
import com.store.model.service.ProductService;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;

/**
 * Product list command
 * This command implements functionality of showing list of products by color.
 * It retrieves all products by size per page and sends them to jsp to show them on page.
 *
 * @author Alexander Mulyk
 * @since 2021-06-14
 */
public class ProductsBySizeCommand implements Command {

    /**
     * Local variable to use product service in command
     */
    private final ProductService productService;

    /**
     * Logger instance to control proper work
     */
    private static final Logger log = Logger.getLogger(ProductListCommand.class);

    /**
     * Constant variable to leave only size name and use it later in service.
     */
    private static final int SUBSTRING_INDEX = "/app/products/size/".length();

    /**
     * Constructor, which initializes productService variable
     * @param productService - product service instance
     */
    public ProductsBySizeCommand(ProductService productService) {
        this.productService = productService;
    }

    /**
     * Implementation of execute command of Command interface. It collects some parameters and uses service method to
     * list products of chosen size sorted by some parameter and sends the list to jsp.
     * @param request HttpServletRequest instance
     * @param response HttpServletResponse instance
     * @return path to the page
     * @throws DatabaseException if service methods get errors
     */
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws DatabaseException {
        String sizeUrl = null;
        try {
            sizeUrl = URLDecoder.decode(request.getRequestURI().substring(SUBSTRING_INDEX), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        log.debug("Size command starts");
        int page = 1;
        if(request.getParameter("page") != null) {
            page = Integer.parseInt(request.getParameter("page"));
        }
        request.setAttribute("currentPage", page);
        log.trace("Set the request attribute: currentPage --> " + page);
        log.trace("sizeUrl --> " + sizeUrl);
        int totalCount = productService.countProductsBySize(sizeUrl);
        request.setAttribute("pageCount", CommandUtils.getPageCount(totalCount,8));

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
        String locale = CommandUtils.checkForLocale(request);
        List<Product> products = productService.listProductsPerPageBySize(page, sizeUrl , sort, direction, locale);
        request.setAttribute("sortWay",request.getParameter("parameter"));
        request.setAttribute("sortDirection",request.getParameter("sortDirection"));
        request.setAttribute("products" , products);
        CommandUtils.setAttributes(request, productService);

        request.setAttribute("selectedCategoryUrl", sizeUrl);
        return "/WEB-INF/product-list.jsp";
    }
}
