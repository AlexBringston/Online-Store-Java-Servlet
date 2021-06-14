package com.store.controller.commands.admin;

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
 * Manage products command
 * This command implements functionality of showing all products.
 * Command performs actions to retrieve a constant number of products per page and send it to jsp to list them.
 *
 * @author Alexander Mulyk
 * @since 2021-06-14
 */
public class ManageProductsCommand implements Command {

    /**
     * Local variable to use product service in command
     */
    private final ProductService productService;

    /**
     * Logger instance to control proper work
     */
    private static final Logger log = Logger.getLogger(ManageProductsCommand.class);

    /**
     * Constructor, which initializes productService variable
     * @param productService - product service instance
     */
    public ManageProductsCommand(ProductService productService) {
        this.productService = productService;
    }

    /**
     * implementation of execute command of Command interface. It gets the page number
     * and displays all products for current page.
     * @param request HttpServletRequest instance
     * @param response HttpServletResponse instance
     * @return path to the page
     * @throws DatabaseException if service methods get errors
     */
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws DatabaseException {
        int page = 1;
        if (request.getParameter("page") != null) {
            page = Integer.parseInt(request.getParameter("page"));
        }
        request.setAttribute("currentPage", page);
        int totalCount = productService.countAllProducts();
        request.setAttribute("pageCount", CommandUtils.getPageCount(totalCount, Utils.ADMIN_PRODUCTS_PER_PAGE));

        String locale = CommandUtils.checkForLocale(request);
        List<Product> products = productService.listProductsPerPage(page, Utils.ADMIN_PRODUCTS_PER_PAGE, "id", "ASC",
                locale);
        request.setAttribute("products", products);
        return "/WEB-INF/admin/products.jsp";
    }
}
