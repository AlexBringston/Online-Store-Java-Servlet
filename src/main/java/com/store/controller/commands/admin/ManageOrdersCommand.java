package com.store.controller.commands.admin;

import com.store.controller.commands.Command;
import com.store.model.service.OrderService;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ManageOrdersCommand implements Command {

    private OrderService orderService;

    private static final Logger log = Logger.getLogger(ManageOrdersCommand.class);

    public ManageOrdersCommand(OrderService orderService) {
        this.orderService = orderService;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        return null;
    }
}
