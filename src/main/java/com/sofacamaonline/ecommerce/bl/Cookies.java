/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sofacamaonline.ecommerce.bl;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author richard
 */
public class Cookies {

    private static final String COOKIE_NAME = "sco_config";

    public static HashMap<String, String> getCookieValues(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cook : cookies) {
                if ((cook != null) && (cook.getName() != null) && cook.getName().equals(COOKIE_NAME)) {
                    return CookieToHashMap(cook);
                }
            }
        }
        return new HashMap<String, String>();
    }

    public static void clearCookie(HttpServletRequest request, HttpServletResponse response) {
        Cookie[] cookies = request.getCookies();
        for (Cookie cook : cookies) {
            if (cook.getName().equals(COOKIE_NAME)) {
                cook.setMaxAge(0);
                //response.addCookie(cook);
                System.out.println("Borrada la cookie");
            }
        }
    }

    public static boolean setCookieValues(HttpServletResponse response, HashMap<String, String> values) {
        try {
            Cookie cookie = new Cookie(COOKIE_NAME, values.toString());
            response.addCookie(cookie);
        } catch (Exception ex) {
            Logger.getLogger(Cookies.class.getName()).log(Level.INFO, ex.getLocalizedMessage());
            return false;
        }
        return true;
    }

    private static HashMap<String, String> CookieToHashMap(Cookie cookie) {
        HashMap<String, String> result = new HashMap<String, String>();
        if (cookie.getValue() == null || cookie.getValue().isEmpty()) {
            return result;
        }
        for (String parts : cookie.getValue().substring(1, cookie.getValue().length() - 1).split(", ")) {
            String[] items = parts.split("=");
            if (items.length > 1) {
                String clave = items[0];
                String valor = items[1];
                if (clave == null || valor == null) {
                    continue;
                }
                System.out.println("Clave: " + clave);
                System.out.println("Valor: " + valor);
                result.put(clave, valor);
            }
        }
        return result;
    }
}
