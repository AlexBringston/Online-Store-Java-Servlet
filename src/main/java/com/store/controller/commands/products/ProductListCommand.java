package com.store.controller.commands.products;

import com.store.controller.commands.Command;
import com.store.controller.commands.CommandUtils;
import com.store.model.dao.Utils;
import com.store.model.entity.Product;
import com.store.model.exception.DatabaseException;
import com.store.model.service.ProductService;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Product list command
 * This command implements functionality of showing list of all products.
 * It retrieves all products per page and sends them to jsp to show them on page.
 *
 * @author Alexander Mulyk
 * @since 2021-06-14
 */
public class ProductListCommand implements Command {

    /**
     * Local variable to use product service in command
     */
    private final ProductService productService;

    /**
     * Logger instance to control proper work
     */
    private static final Logger log = Logger.getLogger(ProductListCommand.class);

    /**
     * Constructor, which initializes productService variable
     * @param productService - product service instance
     */
    public ProductListCommand(ProductService productService) {
        this.productService = productService;
    }

    /**
     * Implementation of execute command of Command interface. It collects some parameters and uses service method to
     * list products sorted by some parameter and sends the list to jsp.
     * @param request HttpServletRequest instance
     * @param response HttpServletResponse instance
     * @return path to the page
     * @throws DatabaseException if service methods get errors
     */
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws DatabaseException {
        log.debug("Commands starts");
        int page = 1;
        if (request.getParameter("page") != null) {
            page = Integer.parseInt(request.getParameter("page"));
        }

        String sort = request.getParameter("parameter");
        String direction = null;
        String orderDirection = request.getParameter("sortDirection");
        if (sort != null && orderDirection != null) {
            sort = sort.toLowerCase();
            direction = productService.getSortDirection(orderDirection);
        }
        if (sort == null || sort.equals("")) {
            sort = "id";
        }
        if (direction == null || direction.equals("")) {
            direction = "ASC";
        }

        request.setAttribute("sortWay", request.getParameter("parameter"));
        request.setAttribute("sortDirection", request.getParameter("sortDirection"));
        request.setAttribute("currentPage", page);
        int totalCount = productService.countAllProducts();
        request.setAttribute("pageCount", CommandUtils.getPageCount(totalCount, Utils.PRODUCTS_PER_PAGE));
        String locale = CommandUtils.checkForLocale(request);
        List<Product> products = productService.listProductsPerPage(page, Utils.PRODUCTS_PER_PAGE, sort, direction,
                locale);

        request.setAttribute("products", products);

        CommandUtils.setAttributes(request, productService);

        log.debug("Commands finished");
        return "/WEB-INF/product-list.jsp";
    }


}
