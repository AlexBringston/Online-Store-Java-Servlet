package com.store.controller.commands.admin;

import com.store.controller.commands.Command;
import com.store.controller.commands.CommandUtils;
import com.store.model.dao.Utils;
import com.store.model.entity.User;
import com.store.model.service.UserService;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class ManageUsersCommand implements Command {

    private UserService userService;

    private static final Logger log = Logger.getLogger(ManageUsersCommand.class);

    public ManageUsersCommand(UserService userService) {
        this.userService = userService;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        int page = 1;
        if (request.getParameter("page") != null) {
            page = Integer.parseInt(request.getParameter("page"));
        }
        request.setAttribute("currentPage", page);
        int totalCount = userService.countAllUsers();
        request.setAttribute("pageCount", CommandUtils.getPageCount(totalCount, Utils.USERS_PER_PAGE));

        List<User> users = userService.listUsers(page, Utils.USERS_PER_PAGE);
        request.setAttribute("users", users);
        return "/WEB-INF/admin/users.jsp";
    }
}
