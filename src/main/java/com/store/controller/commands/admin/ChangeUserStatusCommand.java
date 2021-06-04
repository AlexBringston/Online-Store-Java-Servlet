package com.store.controller.commands.admin;

import com.store.controller.commands.Command;
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
        HttpSession session = request.getSession();
        int userId = Integer.parseInt(request.getParameter("id"));
        User user = userService.findUserById(userId);
        System.out.println("USER CURRENT STATE: "+ user);
        log.info("user status: ->>>>" + user.getStatus());
        if (user.getStatus().equals("ACTIVATED")) {
            log.info("WAS ACTIVATED");
            user.setStatus("BLOCKED");
        }
        else if (user.getStatus().equals("BLOCKED")) {
            user.setStatus("ACTIVATED");
        }
        System.out.println("USER NEW STATE: "+ user);
        userService.updateUser(user);
        session.setAttribute("user",user);

        return "redirect:/manageUsers";
    }
}
