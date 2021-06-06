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

public class ManageProductsCommand implements Command {

    private ProductService productService;

    private static final Logger log = Logger.getLogger(ManageProductsCommand.class);

    public ManageProductsCommand(ProductService productService) {
        this.productService = productService;
    }

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
