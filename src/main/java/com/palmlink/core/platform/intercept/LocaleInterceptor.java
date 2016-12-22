package com.palmlink.core.platform.intercept;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.util.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.servlet.support.RequestContextUtils;

import com.palmlink.core.util.I18nUtil;

/**
 * intercepter to inject the locale to the i18nUtil according the request
 * 
 * @author simon.fu
 * 
 */
public class LocaleInterceptor extends HandlerInterceptorAdapter {

    @Inject
    private I18nUtil i18nUtil;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (handler instanceof HandlerMethod) {
            /* Handle the Request contains locale Info.http://ds.comtest?local=zh_CN. */
            String newLocale = request.getParameter("locale");
            LocaleResolver localeResolver = RequestContextUtils.getLocaleResolver(request);
            if (localeResolver == null) {
                throw new IllegalStateException("No LocaleResolver found");
            }
            if (newLocale != null) {
                // Change current Local
                localeResolver.setLocale(request, response, StringUtils.parseLocaleString(newLocale));
                i18nUtil.setLocale(StringUtils.parseLocaleString(newLocale));
            } else {
                i18nUtil.setLocale(request.getLocale());
            }
        }
        return true;
    }

}
