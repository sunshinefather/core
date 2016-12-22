package com.palmlink.core.platform.monitor;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertThat;
import static org.junit.matchers.JUnitMatchers.containsString;

/**
 * @author Shihai.Fu
 */
public class ExceptionStatisticTest {
    ExceptionStatistic exceptionStatistic;

    @Before
    public void createExceptionStatistic() {
        exceptionStatistic = new ExceptionStatistic();
    }

    public static class TestException1 extends Exception {
        public TestException1(String message) {
            super(message);
        }
    }

    public static class TestException2 extends Exception {
        public TestException2(String message) {
            super(message);
        }
    }

    @Test
    public void toXML() {
        exceptionStatistic.failed(new TestException1("test-message-1"));
        exceptionStatistic.failed(new TestException2("test-message-2"));

        String xml = exceptionStatistic.toXML();
        System.err.println(xml);

        assertThat(xml, containsString("<totalCount>2</totalCount>"));
        assertThat(xml, containsString("<message>test-message-1</message>"));
        assertThat(xml, containsString("<message>test-message-2</message>"));
    }
}
