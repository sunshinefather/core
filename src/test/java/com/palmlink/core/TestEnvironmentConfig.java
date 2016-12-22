package com.palmlink.core;

import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

/**
 * @author Shihai.Fu
 */
@Profile("test")
@Configuration
public class TestEnvironmentConfig {
    @Bean
    public BeanPostProcessor servletContextBeanPostProcessor() {
        return new MockServletContextBeanPostProcessor();
    }
}
