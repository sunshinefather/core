package com.palmlink.core.platform.web;

import java.util.Locale;

import javax.inject.Inject;

import org.springframework.context.i18n.LocaleContextHolder;

import com.palmlink.core.collection.converter.JSONConverter;
import com.palmlink.core.platform.context.Messages;

/**
 * @author Shihai.Fu
 */
public class DefaultController implements SpringController {

    protected Messages messages;

    public <T> T fromString(Class<T> targetClass, String value) {
        return JSONConverter.fromString(targetClass, value);
    }

    protected <T> String toString(T value) {
        return JSONConverter.toString(value);
    }

    protected String getMessage(String messageKey, Object... args) {
        Locale locale = LocaleContextHolder.getLocale();
        return messages.getMessage(messageKey, args, locale);
    }

    @Inject
    public void setMessages(Messages messages) {
        this.messages = messages;
    }
    
}