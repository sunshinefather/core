package com.palmlink.core.log;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.Appender;
import com.palmlink.core.util.AssertUtils;
import com.palmlink.core.util.Convert;
import org.slf4j.MDC;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @author Shihai.Fu
 */
public class TraceLogger {
    public static final String MDC_TARGET_THREAD_ID = "MDC_TARGET_THREAD_ID";
    private static final TraceLogger INSTANCE = new TraceLogger();

    public static TraceLogger get() {
        return INSTANCE;
    }

    private final ConcurrentMap<Long, LoggingEventProcessor> loggingEvents = new ConcurrentHashMap<Long, LoggingEventProcessor>();
    
    private Appender<ILoggingEvent> outputAppender;

    public void process(ILoggingEvent event) {
        long threadId = getTargetThreadId();
        LoggingEventProcessor processor = loggingEvents.get(threadId);

        if (processor == null) {
            // ignore if current thread log is not explicitly initialized
            return;
        }
        processor.process(event);
    }

    private long getTargetThreadId() {
        String targetThreadId = MDC.get(MDC_TARGET_THREAD_ID);
        return Convert.toLong(targetThreadId, Thread.currentThread().getId());
    }

    public void initialize() {
        long threadId = getTargetThreadId();
        loggingEvents.put(threadId, new LoggingEventProcessor(outputAppender));
    }

    public void clear() {
        long threadId = getTargetThreadId();
        loggingEvents.remove(threadId);
    }

    public void clearAll() {
        loggingEvents.clear();
    }

    public void setOutputAppender(Appender<ILoggingEvent> outputAppender) {
        AssertUtils.assertNull(this.outputAppender, "only support single output appender");
        this.outputAppender = outputAppender;
    }
}
