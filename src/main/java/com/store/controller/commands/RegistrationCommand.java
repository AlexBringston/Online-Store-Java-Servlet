package com.store.controller.commands;

import com.store.model.entity.User;
import com.store.model.exception.DatabaseException;
import com.store.model.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Registration command
 * This command implements functionality of creating a new user on website.
 * It returns path to the registration page in general, or, if action parameter from request equals perform, calls
 * corresponding action.
 *
 * @author Alexander Mulyk
 * @since 2021-06-14
 */
public class RegistrationCommand implements Command {

    /**
     * Local variable to use user service in command
     */
    private final UserService userService;

    /**
     * Constructor, which initializes userService variable
     * @param userService - user service instance
     */
    public RegistrationCommand(UserService userService) {
        this.userService = userService;
    }

    /**
     * Implementation of execute command of Command interface. Depending on action, returns either path to the
     * registration page, or calls perform action method and returns redirect to the user page.
     * @param request HttpServletRequest instance
     * @param response HttpServletResponse instance
     * @return path to the page
     * @throws DatabaseException if service methods get errors
     */
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws DatabaseException {
        String action = request.getParameter("action");
        if (action != null && action.equals("perform")) {
            return doPerformAction(request);
        } else {
            return "/registration.jsp";
        }
    }

    /**
     * Method to perform creation of new user and adding the data to the users table
     * @param request HttpServletRequest instance
     * @return redirect path to admin manage products page
     * @throws DatabaseException if service method gets some error
     */
    protected String doPerformAction(HttpServletRequest request) throws DatabaseException {
        HttpSession session = request.getSession();
        String login = request.getParameter("login");
        String password = request.getParameter("password");
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        User user = new User(login, password, firstName, lastName);
        userService.createUser(user);

        session.setAttribute("user", user);

        session.setAttribute("userRole", user.getRole());
        return "redirect:/user";
    }
}
