package com.store.controller.commands;

import com.store.model.entity.Category;
import com.store.model.entity.Color;
import com.store.model.entity.Size;
import com.store.model.service.ProductService;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public final class CommandUtils {

    private static final Logger log = Logger.getLogger(CommandUtils.class);

    public static void setAttributes(HttpServletRequest request, ProductService productService) {
        List<Category> categories = productService.listAllCategories();
        log.trace("categories ->" + categories);
        List<Color> colors = productService.listAllColors();
        log.trace("colors ->" + colors);
        List<Size> sizes = productService.listAllSizes();
        log.trace("sizes ->" + sizes);
        request.setAttribute("categories" , categories);
        request.setAttribute("colors" , colors);
        request.setAttribute("sizes" , sizes);
    }
}
