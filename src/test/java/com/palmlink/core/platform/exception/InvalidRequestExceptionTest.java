package com.palmlink.core.platform.exception;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Shihai.Fu
 */
public class InvalidRequestExceptionTest {
    @Test
    public void createExceptionWithMessageOnly() {
        InvalidRequestException exception = new InvalidRequestException("errorMessage");
        Assert.assertEquals("errorMessage", exception.getMessage());
        Assert.assertNull(exception.getField());
    }
}
