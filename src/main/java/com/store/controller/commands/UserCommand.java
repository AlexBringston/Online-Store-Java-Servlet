package com.store.controller.commands;

import javax.servlet.http.HttpServletRequest;

public class UserCommand implements Command{

    @Override
    public String execute(HttpServletRequest request) {
        return "/WEB-INF/admin/user.jsp";
    }
}
