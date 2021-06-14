package com.store.controller.commands;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Locale command
 * This command implements functionality of changing locale on site.
 *
 * @author Alexander Mulyk
 * @since 2021-06-14
 */
public class LocaleCommand implements Command{

    /**
     * Implementation of execute command of Command interface. It gets new locale value from the request and sets it
     * to the current session if it isn't null, after what calls redirect to previous page.
     * manage products page.
     * @param request HttpServletRequest instance
     * @param response HttpServletResponse instance
     * @return path to the page where change was called
     */
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();

        String locale = request.getParameter("locale");
        if (locale != null) {
            session.setAttribute("locale", locale);
        }
        String referer = request.getHeader("Referer");
        return "redirect:"+referer.substring(referer.indexOf("/app")+"/app".length());
    }
}
