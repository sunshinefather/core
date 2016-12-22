package com.palmlink.core.platform.intercept;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.palmlink.core.platform.service.WSClientService;

/**
 * Intercepter to inject the locale to NstechsApiClientService
 * 
 * @author simon.fu
 * 
 */
public class WSLocaleInterceptor extends HandlerInterceptorAdapter {

    @Inject
    private WSClientService wsClientService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (handler instanceof HandlerMethod)
            wsClientService.setLocale(request.getLocale());
        return true;
    }

}
