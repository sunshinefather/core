package com.palmlink.core;

import com.palmlink.core.platform.DefaultSchedulerConfig;
import com.palmlink.core.platform.JobRegistry;
import org.springframework.context.annotation.Configuration;

/**
 * @author Shihai.Fu
 */
@Configuration
public class TestSchedulerConfig extends DefaultSchedulerConfig {
    @Override
    protected void configure(JobRegistry registry) {
    }
}
