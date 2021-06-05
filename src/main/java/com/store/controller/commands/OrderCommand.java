package com.store.controller.commands;

import com.store.controller.commands.products.ProductListCommand;
import com.store.model.entity.Order;
import com.store.model.entity.OrderItem;
import com.store.model.entity.User;
import com.store.model.service.OrderService;
import com.store.model.service.UserService;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class OrderCommand implements Command{

    private OrderService orderService;

    private static final Logger log = Logger.getLogger(ProductListCommand.class);

    public OrderCommand(OrderService productService) {
        this.orderService = productService;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
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
            orderService.createOrder(order);
            for (OrderItem orderItem : cart) {
                orderItem.setOrderId(order.getId());
                orderService.createOrderItem(orderItem);
            }
            user.setBalance(user.getBalance().subtract(totalSum));
            new UserService().updateUser(user);
            session.removeAttribute("cart");
            return "redirect:/user";

        } else {
            String errorMessage = "You cannot afford this operation, please check your balance";
            request.setAttribute("errorMessage", errorMessage);
            return "/WEB-INF/error.jsp";
        }

    }
}
