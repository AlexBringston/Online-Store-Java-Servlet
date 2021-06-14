package com.store.controller.commands;

import com.store.controller.commands.products.ProductListCommand;
import com.store.model.entity.Order;
import com.store.model.entity.OrderItem;
import com.store.model.entity.User;
import com.store.model.exception.DatabaseException;
import com.store.model.service.OrderService;
import com.store.model.service.UserService;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.List;

/**
 * Order command
 * This command implements functionality of making order.
 * It gets all given data, counts if user can afford this purchase, if true, creates new order, creates every order
 * item with order's id and updates user balance.
 *
 * @author Alexander Mulyk
 * @since 2021-06-14
 */
public class OrderCommand implements Command{

    /**
     * Local variable to use order service in command
     */
    private final OrderService orderService;

    /**
     * Logger instance to control proper work
     */
    private static final Logger log = Logger.getLogger(ProductListCommand.class);

    /**
     * Constructor, which initializes productService variable
     * @param orderService - product service instance
     */
    public OrderCommand(OrderService orderService) {
        this.orderService = orderService;
    }

    /**
     * Implementation of execute command of Command interface. It gets user from session, creates new order object,
     * gets cart from session, count total cost of purchases, checks if user can afford it, and if yes, performs a
     * transaction where creates an order and corresponding order items from given data. After that it sets the
     * success message if operation is performed, or error message if user cannot afford the purchase.
     * @param request HttpServletRequest instance
     * @param response HttpServletResponse instance
     * @return path to the success page
     * @throws DatabaseException if service methods get errors
     */
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws DatabaseException {
        HttpSession session = request.getSession();

        User user = (User) session.getAttribute("user");
        Order order = new Order(user.getId());
        List<OrderItem> cart = (List<OrderItem>)session.getAttribute("cart");
        BigDecimal totalSum = BigDecimal.ZERO;
        String[] quantities = request.getParameterValues("quantity");

        for (int i = 0; i < cart.size(); i++) {
            cart.get(i).setQuantity(Integer.parseInt(quantities[i]));
            totalSum = totalSum.add(cart.get(i).getProduct().getPrice()
                            .multiply(new BigDecimal(cart.get(i).getQuantity())));
        }

        if (user.getBalance().subtract(totalSum).compareTo(BigDecimal.ZERO) >= 0) {
            orderService.createAndFillOrder(order, cart);
            user.setBalance(user.getBalance().subtract(totalSum));
            new UserService().updateUser(user);
            session.removeAttribute("cart");
            request.setAttribute("successMessage", "Operation was successfully made");
            return "redirect:/success";

        } else {
            throw new DatabaseException("You cannot afford this operation, please check your balance");
        }

    }
}
