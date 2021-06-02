package com.store.controller.commands;



import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

public class AuthorizationCommand implements Command{

    private static final Logger log = Logger.getLogger(LoginCommand.class);

    @Override
    public String execute(HttpServletRequest request) {

        return "/login.jsp";
    }
}
