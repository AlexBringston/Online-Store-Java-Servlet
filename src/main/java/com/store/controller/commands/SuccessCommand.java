package com.store.controller.commands;

import com.store.model.exception.DatabaseException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class SuccessCommand implements Command{
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, DatabaseException {
        return "/WEB-INF/success.jsp";
    }
}
