package com.palmlink.core.log;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.Appender;
import ch.qos.logback.core.AppenderBase;
import ch.qos.logback.core.spi.AppenderAttachable;

import java.util.Iterator;

/**
 * @author Shihai.Fu
 */
public class TraceAppender extends AppenderBase<ILoggingEvent> implements AppenderAttachable<ILoggingEvent> {
    @Override
    public void stop() {
        super.stop();

        TraceLogger.get().clearAll();
    }

    @Override
    protected void append(ILoggingEvent event) {
        TraceLogger.get().process(event);
    }

    @Override
    public void addAppender(Appender<ILoggingEvent> appender) {
        TraceLogger.get().setOutputAppender(appender);
    }

    @Override
    public Iterator<Appender<ILoggingEvent>> iteratorForAppenders() {
        throw new IllegalStateException("unexpected flow");
    }

    @Override
    public Appender<ILoggingEvent> getAppender(String name) {
        throw new IllegalStateException("unexpected flow");
    }

    @Override
    public boolean isAttached(Appender<ILoggingEvent> appender) {
        throw new IllegalStateException("unexpected flow");
    }

    @Override
    public void detachAndStopAllAppenders() {
        throw new IllegalStateException("unexpected flow");
    }

    @Override
    public boolean detachAppender(Appender<ILoggingEvent> appender) {
        throw new IllegalStateException("unexpected flow");
    }

    @Override
    public boolean detachAppender(String name) {
        throw new IllegalStateException("unexpected flow");
    }
}
