package com.palmlink.core.platform.scheduler;

import com.palmlink.core.SpringTest;
import org.junit.Assert;
import org.junit.Test;

import javax.inject.Inject;

/**
 * @author Shihai.Fu
 */
public class SchedulerTest extends SpringTest {
    @Inject
    Scheduler scheduler;

    @Test
    public void schedulerShouldBeRegistered() {
        Assert.assertNotNull("Scheduler should be registered by DefaultSchedulerConfig", scheduler);
    }
}
