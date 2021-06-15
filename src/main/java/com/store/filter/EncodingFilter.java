package com.store.filter;

import javax.servlet.*;
import java.io.IOException;

/**
 * Encoding filter.
 * Encoding filter is used to set request and response encodings to UTF-8 to correctly display latin and cyrillic
 * letters.
 * @author Alexander Mulyk
 * @since 2021-06-14
 */
public class EncodingFilter implements Filter {

    /**
     * Filter method sets request and response encodings to UTF-8
     * @param request ServletRequest instance
     * @param response ServletResponse instance
     * @param chain filter chain
     * @throws IOException if cannot go further by filter chain
     * @throws ServletException if chain cannot work as programmed
     */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        response.setContentType("text/html");
        response.setCharacterEncoding("UTF-8");
        request.setCharacterEncoding("UTF-8");
        request.setCharacterEncoding("UTF-8");
        chain.doFilter(request, response);
    }

}