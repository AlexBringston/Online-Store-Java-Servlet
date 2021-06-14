package com.store.controller.commands;

import com.store.model.entity.Category;
import com.store.model.entity.Color;
import com.store.model.entity.OrderItem;
import com.store.model.entity.Size;
import com.store.model.exception.DatabaseException;
import com.store.model.service.ProductService;
import org.apache.log4j.Logger;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * Utils class with methods which are used in different commands.
 *
 * @author Alexander Mulyk
 * @since 2021-06-14
 */
public final class CommandUtils {

    /**
     * Logger instance to control proper work
     */
    private static final Logger log = Logger.getLogger(CommandUtils.class);

    /**
     * Method to set multiple attributes for product page to avoid code duplication.
     * @param request HttpServletRequest instance
     * @param productService - product service instance
     * @throws DatabaseException if service methods get errors
     */
    public static void setAttributes(HttpServletRequest request, ProductService productService) throws DatabaseException {
        String locale = checkForLocale(request);
        log.info("locale -> " +locale);
        List<Category> categories = productService.listAllCategories(locale);
        List<Color> colors = productService.listAllColors(locale);
        List<Size> sizes = productService.listAllSizes(locale);
        request.setAttribute("categories" , categories);
        request.setAttribute("colors" , colors);
        request.setAttribute("sizes" , sizes);
    }

    /**
     * Method to serialize cart items in string in order to cache them and not lose them after user logout.
     * @param cart List of OrderItem objects
     * @return string which contains serialized cart items
     */
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

    /**
     * Method to deserialize cart items from given string.
     * @param request HttpServletRequest instance
     * @param string String which contains serialized cart items
     * @return list of deserialized cart OrderItem items
     */
    public static List<OrderItem> deserializeCart(HttpServletRequest request, String string) throws DatabaseException {
        List<OrderItem> cart = new ArrayList<>();
        String[] array = string.split("\\|");
        String locale = CommandUtils.checkForLocale(request);
        for (String item : array) {
            try {
                String[] data = item.split("\\*");
                log.trace("data[0] "+ data[0]);
                log.trace("data[1] "+ data[1]);
                int idProduct = Integer.parseInt(data[0]);
                int count = Integer.parseInt(data[1]);
                cart.add(new OrderItem(new ProductService().getProductById(idProduct, locale),count));
            } catch (RuntimeException e) {
                log.error("Can't add product to ShoppingCart during deserialization: item=" + item, e);
            }
        }
        return cart;
    }

    /**
     * Method to check if product with given id is present in the cart
     * @param id product id
     * @param cart list of cart items
     * @return index of product in cart if found, otherwise return -1
     */
    public static int isExisting(int id, List<OrderItem> cart) {
        for (int i = 0; i < cart.size(); i++) {
            if (cart.get(i).getProduct().getId() == id) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Method to find a cookie with given name
     * @param cookies all present cookies
     * @param cookieName name of searched cookie
     * @return cookie
     */
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

    /**
     * Method to get total pages number
     * @param total number of items in total
     * @param numberPerPage number of items per page
     * @return page count
     */
    public static int getPageCount(int total, int numberPerPage) {
        int pageCount = total / numberPerPage;
        if(pageCount * numberPerPage != total) {
            pageCount++;
        }
        return pageCount;
    }

    /**
     * Method to check for the locale in session
     * @param request HttpServletRequest instance
     * @return locale name
     */
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
