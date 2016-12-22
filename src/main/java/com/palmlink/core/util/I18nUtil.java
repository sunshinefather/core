package com.palmlink.core.util;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.support.ResourceBundleMessageSource;

/**
 * i18n utility to get the message due to the injected locale
 * 
 * @author simon.fu
 * 
 */
public class I18nUtil {

    @Autowired
    @Qualifier("messageSource")
    private ResourceBundleMessageSource messageSource;

    private Locale locale;

    // get message with the current locale

    public String getMessage(String code) {
        return messageSource.getMessage(code, null, Locale.CHINESE);
    }

    public String getMessage(String code, Object[] obj) {
        return messageSource.getMessage(code, obj, Locale.CHINESE);
    }

    public String message(String key, String args) {
        return String.format(this.getMessage(key), args);
    }

    // get message with locale explicitly

    public String getMessage(String code, Locale locale) {
        return messageSource.getMessage(code, null, locale);
    }

    public String getMessage(String code, Object[] obj, Locale locale) {
        return messageSource.getMessage(code, obj, locale);
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
    }

    public Locale getLocale() {
        return locale;
    }
    
}
