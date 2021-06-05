package com.store.controller.commands;

import com.store.controller.commands.products.ProductListCommand;
import com.store.model.entity.OrderItem;
import com.store.model.service.ProductService;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

public class CartCommand implements Command{

    private ProductService productService;

    private static final Logger log = Logger.getLogger(ProductListCommand.class);

    public CartCommand(ProductService productService) {
        this.productService = productService;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String action = request.getParameter("action");
        String forward = null;
        if (action == null) {
            forward = "/WEB-INF/shopping-cart.jsp";
        } else {
            if (action.equalsIgnoreCase("buy")) {
                forward = doBuyAction(request);
            } else if (action.equalsIgnoreCase("remove")) {
                forward = doRemoveAction(request);
            }
        }
        return forward;
    }

    protected String doRemoveAction(HttpServletRequest request) {
        HttpSession session = request.getSession();
        List<OrderItem> cart = (List<OrderItem>)session.getAttribute("cart");
        int index = CommandUtils.isExisting(Integer.parseInt(request.getParameter("id")), cart);
        cart.remove(index);
        session.setAttribute("cart", cart);
        return "redirect:/cart";
    }

    protected String doBuyAction(HttpServletRequest request) {
        HttpSession session = request.getSession();
        String locale = CommandUtils.checkForLocale(request);
        if (session.getAttribute("cart") == null) {
            List<OrderItem> cart = new ArrayList<>();
            int id = Integer.parseInt(request.getParameter("id"));
            cart.add(new OrderItem(productService.getProductById(id, locale),1));
            session.setAttribute("cart", cart);
        } else {
            List<OrderItem> cart = (List<OrderItem>)session.getAttribute("cart");
            int index = CommandUtils.isExisting(Integer.parseInt(request.getParameter("id")), cart);
            if (index == -1) {
                cart.add(new OrderItem(productService.getProductById(Integer.parseInt(request.getParameter("id")),
                        locale),1));
            } else {
                int quantity = cart.get(index).getQuantity() + 1;
                cart.get(index).setQuantity(quantity);
            }
            session.setAttribute("cart", cart);
        }
        return "redirect:"+request.getHeader("referer").substring(request.getHeader("referer").indexOf("/products"));
    }

}
