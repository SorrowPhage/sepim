package com.sepim.springboot.utils;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class MySessionUtil {

    public static HttpSession getHttpSession() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
                .getRequest();
        return request.getSession();
    }

    public static void setSession(String key, Object value) {
        getHttpSession().setAttribute(key, value);
    }

    public static Object getSession(String key) {
        return getHttpSession().getAttribute(key);
    }

    public static void removeSession(String key) {
        getHttpSession().removeAttribute(key);
    }

}
