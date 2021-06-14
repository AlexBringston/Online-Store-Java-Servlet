package com.store.controller.commands;

import com.store.model.entity.User;
import com.store.model.exception.DatabaseException;
import com.store.model.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;

/**
 * Make deposit command
 * This command implements functionality of changing user balance.
 *
 * @author Alexander Mulyk
 * @since 2021-06-14
 */
public class MakeDepositCommand implements Command{

    /**
     * Local variable to use user service in command
     */
    private final UserService userService;

    /**
     * Constructor, which initializes userService variable
     * @param userService - user service instance
     */
    public MakeDepositCommand(UserService userService) {
        this.userService = userService;
    }

    /**
     * Implementation of execute command of Command interface. It gets an amount from request, current user balance
     * from session and, if amount is positive, adds this amount to user balance and updates it in database.
     * @param request HttpServletRequest instance
     * @param response HttpServletResponse instance
     * @return redirect to the user page
     * @throws DatabaseException if service methods get errors
     */
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
