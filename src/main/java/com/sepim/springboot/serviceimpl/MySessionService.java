package com.sepim.springboot.serviceimpl;

import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Service
public class MySessionService {

    public static HttpSession getHttpSession() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
                .getRequest();
        return request.getSession();
    }

    public void setSession(String key, Object value) {
        getHttpSession().setAttribute(key, value);
    }

    public Object getSession(String key) {
        return getHttpSession().getAttribute(key);
    }

    public void removeSession(String key) {
        getHttpSession().removeAttribute(key);
    }

}
