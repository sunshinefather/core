package com.palmlink.core;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.mock.web.MockRequestDispatcher;
import org.springframework.web.context.ServletContextAware;

import javax.servlet.RequestDispatcher;

/**
 * @author Shihai.Fu
 */
public class MockServletContextBeanPostProcessor implements BeanPostProcessor {
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        if (bean instanceof ServletContextAware) {
            ((ServletContextAware) bean).setServletContext(new MockServletContext());
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    public static class MockServletContext extends org.springframework.mock.web.MockServletContext {
        @Override
        public RequestDispatcher getNamedDispatcher(String path) {
            // simulate tomcat default servlet
            if ("default".equals(path)) return new MockRequestDispatcher(path);
            return null;
        }
    }
}
