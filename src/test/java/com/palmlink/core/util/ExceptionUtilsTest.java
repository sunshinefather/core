package com.palmlink.core.util;

import org.junit.Assert;
import org.junit.Test;
import org.junit.matchers.JUnitMatchers;

/**
 * @author Shihai.Fu
 */
public class ExceptionUtilsTest {
    @Test
    public void getStackTrace() {
        RuntimeException exception = new RuntimeException();
        String trace = ExceptionUtils.stackTrace(exception);

        System.err.println(trace);

        Assert.assertThat(trace, JUnitMatchers.containsString(RuntimeException.class.getName()));
    }
}
