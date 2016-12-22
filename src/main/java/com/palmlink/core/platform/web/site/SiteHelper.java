package com.palmlink.core.platform.web.site;

import org.springframework.web.method.HandlerMethod;

import com.palmlink.core.platform.web.SpringController;

public class SiteHelper {
    
    public static boolean isSiteController(Object handler) {
        return handler instanceof HandlerMethod && (((HandlerMethod) handler).getBean() instanceof SpringController);
    }

}