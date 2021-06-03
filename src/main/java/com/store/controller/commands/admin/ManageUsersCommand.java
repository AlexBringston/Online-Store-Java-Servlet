package com.store.controller.commands.admin;

import com.store.controller.commands.Command;
import com.store.model.service.UserService;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ManageUsersCommand implements Command {

    private UserService userService;

    private static final Logger log = Logger.getLogger(ManageUsersCommand.class);

    public ManageUsersCommand(UserService userService) {
        this.userService = userService;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        return null;
    }
}
