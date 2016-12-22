package com.palmlink.core.platform.scheduler;

import com.palmlink.core.util.TimeLength;

import java.util.Date;

/**
 * @author Shihai.Fu
 */
public interface Scheduler {
    void triggerOnce(Job job);

    void triggerOnceAt(Job job, Date time);

    void triggerOnceWithDelay(Job job, TimeLength delay);
}
