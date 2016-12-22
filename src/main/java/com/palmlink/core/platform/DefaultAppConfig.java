package com.palmlink.core.platform;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import com.palmlink.core.http.HTTPClient;
import com.palmlink.core.platform.context.Messages;
import com.palmlink.core.platform.context.PropertyContext;
import com.palmlink.core.platform.monitor.ExceptionStatistic;
import com.palmlink.core.platform.scheduler.Scheduler;
import com.palmlink.core.platform.scheduler.SchedulerImpl;
import com.palmlink.core.platform.service.WSClientService;
import com.palmlink.core.template.FreemarkerTemplate;

public class DefaultAppConfig {

    @Bean
    public static PropertyContext propertyContext() throws IOException {
        PropertyContext propertySource = new PropertyContext();
        propertySource.setLocations(new PathMatchingResourcePatternResolver().getResources("classpath*:*.properties"));
        return propertySource;
    }

    @Bean(name="messageSource")
    public Messages messages() throws IOException {
        Resource[] messageResources = new PathMatchingResourcePatternResolver().getResources("classpath*:messages/*.properties");
        Messages messages = new Messages();
        Set<String> names=new HashSet<String>();
        for (int i = 0, messageResourcesLength = messageResources.length; i < messageResourcesLength; i++) {
            Resource messageResource = messageResources[i];
            String filename = messageResource.getFilename();
            names.add("messages/" + filename.split("[_]")[0]);
        }
        String[] baseNames = (String[])names.toArray();
        messages.setBasenames(baseNames);
        return messages;
    }

    @Bean
    public SpringObjectFactory springObjectFactory() {
        return new SpringObjectFactory();
    }

    @Bean
    public ExceptionStatistic exceptionStatistic() {
        return new ExceptionStatistic();
    }

    @Bean
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public HTTPClient httpClient() {
        return new HTTPClient();
    }

    @Bean
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public FreemarkerTemplate freemarkerTemplate() {
        return new FreemarkerTemplate();
    }

    @Bean
    public Scheduler scheduler() {
        return new SchedulerImpl();
    }

    @Bean
    public WSClientService wsClientService() {
        return new WSClientService();
    }

}