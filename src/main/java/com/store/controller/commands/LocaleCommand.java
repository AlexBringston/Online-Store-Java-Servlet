package com.store.controller.commands;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class LocaleCommand implements Command{
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession();

        String locale = request.getParameter("locale");
        if (locale != null) {
            session.setAttribute("locale", locale);
        }
        String referer = request.getHeader("Referer");
        return "redirect:"+referer.substring(referer.indexOf("/app")+"/app".length());
    }
}
