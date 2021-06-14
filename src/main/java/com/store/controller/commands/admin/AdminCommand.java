package com.store.controller.commands.admin;

import com.store.controller.commands.Command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Admin command
 * This command returns path to the admin page.
 *
 * @author Alexander Mulyk
 * @since 2021-06-14
 */
public class AdminCommand implements Command {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        return "/WEB-INF/admin/admin.jsp";
    }
}
