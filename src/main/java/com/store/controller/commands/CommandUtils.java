package com.store.controller.commands;

import com.oracle.wls.shaded.org.apache.xpath.operations.Or;
import com.store.model.entity.Category;
import com.store.model.entity.Color;
import com.store.model.entity.OrderItem;
import com.store.model.entity.Size;
import com.store.model.service.ProductService;
import org.apache.log4j.Logger;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

public final class CommandUtils {

    private static final Logger log = Logger.getLogger(CommandUtils.class);

    public static void setAttributes(HttpServletRequest request, ProductService productService) {
        String locale = checkForLocale(request);
        log.info("locale -> " +locale);
        List<Category> categories = productService.listAllCategories(locale);
        List<Color> colors = productService.listAllColors(locale);
        List<Size> sizes = productService.listAllSizes(locale);
        request.setAttribute("categories" , categories);
        request.setAttribute("colors" , colors);
        request.setAttribute("sizes" , sizes);
    }

    public static String serializeCart(List<OrderItem> cart) {

        StringBuilder sb = new StringBuilder();
        for (OrderItem orderItem: cart) {
            sb.append(orderItem.getProduct().getId()).append("*").append(orderItem.getQuantity()).append("|");
        }
        if (sb.length() == 0) {
            return "";
        }
        return sb.deleteCharAt(sb.length()-1).toString();
    }

    public static List<OrderItem> deserializeCart(String string) {
        List<OrderItem> cart = new ArrayList<>();
        String[] array = string.split("\\|");

        for (String item : array) {
            try {
                String[] data = item.split("\\*");
                log.trace("data[0] "+ data[0]);
                log.trace("data[1] "+ data[1]);
                int idProduct = Integer.parseInt(data[0]);
                int count = Integer.parseInt(data[1]);
                cart.add(new OrderItem(new ProductService().getProductById(idProduct),count));
            } catch (RuntimeException e) {
                log.error("Can't add product to ShoppingCart during deserialization: item=" + item, e);
            }
        }
        return cart;
    }

    public static int isExisting(int id, List<OrderItem> cart) {
        for (int i = 0; i < cart.size(); i++) {
            if (cart.get(i).getProduct().getId() == id) {
                return i;
            }
        }
        return -1;
    }

    public static Cookie findCookie(Cookie[] cookies, String cookieName) {
        Cookie cookie = null;
        if(cookies !=null) {
            for(Cookie c: cookies) {
                if(cookieName.equals(c.getName())) {
                    cookie = c;
                    break;
                }
            }
        }
        return cookie;
    }

    public static int getPageCount(int total, int numberPerPage) {
        int pageCount = total / numberPerPage;
        if(pageCount * numberPerPage != total) {
            pageCount++;
        }
        return pageCount;
    }

    public static boolean checkUserIsLogged(HttpServletRequest request, String userName){
        HashSet<String> loggedUsers = (HashSet<String>) request.getSession().getServletContext()
                .getAttribute("loggedUsers");
        if (loggedUsers == null) {
            loggedUsers = new HashSet<>();
        }
        else if(loggedUsers.stream().anyMatch(userName::equals)){
            return true;
        }
        loggedUsers.add(userName);
        request.getSession().getServletContext()
                .setAttribute("loggedUsers", loggedUsers);
        return false;
    }

    public static String checkForLocale(HttpServletRequest request) {
        String locale = (String)request.getSession().getAttribute("locale");
        if (locale != null && !(locale.equals("")||locale.equals("english"))) {
            locale = "_" + locale.substring(0,2);
        } else {
            locale = "";
        }
        return locale;
    }

}
