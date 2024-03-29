package com.store.controller.commands.admin;

import com.store.controller.commands.Command;
import com.store.controller.commands.CommandUtils;
import com.store.model.dao.Utils;
import com.store.model.entity.User;
import com.store.model.exception.DatabaseException;
import com.store.model.service.UserService;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Manage users command
 * This command implements functionality of showing all users.
 * Command performs actions to retrieve a constant number of users per page and send it to jsp to list them.
 *
 * @author Alexander Mulyk
 * @since 2021-06-14
 */
public class ManageUsersCommand implements Command {

    /**
     * Local variable to use user service in command
     */
    private final UserService userService;

    /**
     * Logger instance to control proper work
     */
    private static final Logger log = Logger.getLogger(ManageUsersCommand.class);

    /**
     * Constructor, which initializes userService variable
     * @param userService - user service instance
     */
    public ManageUsersCommand(UserService userService) {
        this.userService = userService;
    }

    /**
     * implementation of execute command of Command interface. It gets the page number
     * and displays all users for current page.
     * @param request HttpServletRequest instance
     * @param response HttpServletResponse instance
     * @return path to the page
     * @throws DatabaseException if service methods get errors
     */
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws DatabaseException {
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
