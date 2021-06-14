package com.store.controller.commands.admin;

import com.store.controller.commands.Command;
import com.store.controller.commands.CommandUtils;
import com.store.model.entity.User;
import com.store.model.exception.DatabaseException;
import com.store.model.service.UserService;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Change user status command
 * This command implements functionality of changing a status of some existing user.
 * It performs some simple actions of changing user status and updating it in database.
 *
 * @author Alexander Mulyk
 * @since 2021-06-14
 */
public class ChangeUserStatusCommand implements Command {

    /**
     * Local variable to use user service in command
     */
    private final UserService userService;

    /**
     * Logger instance to control proper work
     */
    private static final Logger log = Logger.getLogger(ChangeUserStatusCommand.class);

    /**
     * Constructor, which initializes userService variable
     * @param userService - user service instance
     */
    public ChangeUserStatusCommand(UserService userService) {
        this.userService = userService;
    }

    /**
     * Implementation of execute command of Command interface. Changes user status to the opposite and returns
     * redirect to manage users page
     * @param request HttpServletRequest instance
     * @param response HttpServletResponse instance
     * @return path to the page
     * @throws DatabaseException if service methods get errors
     */
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, DatabaseException {
        int userId = Integer.parseInt(request.getParameter("id"));
        String locale = CommandUtils.checkForLocale(request);
        User user = userService.findUserById(userId, locale);

        if (user.getStatus().equals("Activated")) {
            user.setStatus("Blocked");
        }
        else if (user.getStatus().equals("Blocked")) {
            user.setStatus("Activated");
        }

        userService.updateUser(user);

        return "redirect:/manageUsers";
    }
}
