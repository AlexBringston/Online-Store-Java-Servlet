package com.store.controller.commands;

import com.store.controller.commands.products.ProductListCommand;
import com.store.model.entity.OrderItem;
import com.store.model.exception.DatabaseException;
import com.store.model.service.ProductService;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

/**
 * Cart command.
 * This command implements functionality of operations with cart. There are multiple actions which give user
 * opportunity to delete a product from cart, to create an order or to simply get to the cart page.
 *
 * @author Alexander Mulyk
 * @since 2021-06-14
 */
public class CartCommand implements Command{

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
    public CartCommand(ProductService productService) {
        this.productService = productService;
    }

    /**
     * Implementation of execute command of Command interface. Depending on action, returns either page path, on which
     * user can make order, or delete a product from cart.
     * @param request HttpServletRequest instance
     * @param response HttpServletResponse instance
     * @return path to the page
     * @throws DatabaseException if service methods get errors
     */
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws DatabaseException {
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

    /**
     * Method to implement functionality of remove product action, which gets a cart from current session, looks for
     * product by index and removes it, after that sets new cart to the session.
     * @param request HttpServletRequest instance
     * @return redirect path to the cart
     */
    protected String doRemoveAction(HttpServletRequest request) {
        HttpSession session = request.getSession();
        List<OrderItem> cart = (List<OrderItem>)session.getAttribute("cart");
        int index = CommandUtils.isExisting(Integer.parseInt(request.getParameter("id")), cart);
        cart.remove(index);
        session.setAttribute("cart", cart);
        return "redirect:/cart";
    }

    /**
     * Method to implement functionality of creating an order, by checking cart in session and, if it's not set yet,
     * creating new cart, adding product and setting back to session. If the cart has anything in it, it will be read
     * as list, given product will be added to it. After that new list will be set in session. In case of this action
     * user will be redirected to the page where 'buy' button was clicked.
     * @param request HttpServletRequest instance
     * @return redirect path to the cart
     */
    protected String doBuyAction(HttpServletRequest request) throws DatabaseException {
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
