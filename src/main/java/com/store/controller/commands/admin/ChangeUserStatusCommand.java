package com.store.controller.commands.admin;

import com.store.controller.commands.Command;
import com.store.controller.commands.CommandUtils;
import com.store.model.entity.User;
import com.store.model.service.UserService;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class ChangeUserStatusCommand implements Command {

    private UserService userService;

    private static final Logger log = Logger.getLogger(ChangeUserStatusCommand.class);

    public ChangeUserStatusCommand(UserService userService) {
        this.userService = userService;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
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
