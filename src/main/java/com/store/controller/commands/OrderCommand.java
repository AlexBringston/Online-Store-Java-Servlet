package com.store.controller.commands;

import com.store.controller.commands.products.ProductListCommand;
import com.store.model.entity.Order;
import com.store.model.entity.OrderItem;
import com.store.model.entity.User;
import com.store.model.service.OrderService;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
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
        orderService.createOrder(order);
        List<OrderItem> cart = (List<OrderItem>)session.getAttribute("cart");
        String[] quantities = request.getParameterValues("quantity");

        for (int i = 0; i < cart.size(); i++) {
            cart.get(i).setOrderId(order.getId());
            cart.get(i).setQuantity(Integer.parseInt(quantities[i]));
            orderService.createOrderItem(cart.get(i));
        }
        session.removeAttribute("cart");
        return "redirect:/user";
    }
}
