package com.store.controller.commands;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Success command
 * This command returns path to the success page.
 *
 * @author Alexander Mulyk
 * @since 2021-06-14
 */
public class SuccessCommand implements Command{
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        return "/WEB-INF/success.jsp";
    }
}
