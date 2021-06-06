package com.store.controller.commands;

import com.store.model.entity.User;
import com.store.model.exception.DatabaseException;
import com.store.model.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;

public class MakeDepositCommand implements Command{

    private UserService userService;

    public MakeDepositCommand(UserService userService) {
        this.userService = userService;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, DatabaseException {
        BigDecimal amount = new BigDecimal(request.getParameter("amount"));
        User user = (User)request.getSession().getAttribute("user");
        BigDecimal balance = user.getBalance();
        if (amount.compareTo(BigDecimal.ZERO) > 0) {
            user.setBalance(balance.add(amount));
            userService.updateUser(user);
        }

        return "redirect:/user";
    }
}
