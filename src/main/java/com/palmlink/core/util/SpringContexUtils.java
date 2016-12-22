package com.palmlink.core.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class SpringContexUtils implements ApplicationContextAware {
    
    private ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        synchronized (SpringContexUtils.class) {
            this.applicationContext = applicationContext;
        }
    }
    
    public  ApplicationContext getApplicationContext() {
        return applicationContext;
    }
    
    public  <T> T getBean(Class<T> requiredType) {
        return applicationContext.getBean(requiredType);
    }

    public  <T> T getBean(String name) {
        return (T) applicationContext.getBean(name);
    }
    
    public  <T> T getBean(String name, Class<T> requiredType) {
        return applicationContext.getBean(name, requiredType);
    }
    
}