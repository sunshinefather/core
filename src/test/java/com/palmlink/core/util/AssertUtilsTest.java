package com.palmlink.core.util;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

/**
 * @author Shihai.Fu
 */
public class AssertUtilsTest {
    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Test
    public void assertFalse() {
        exception.expect(AssertUtils.AssertionException.class);
        exception.expectMessage("someErrorMessage");

        AssertUtils.assertFalse(true, "someErrorMessage");
    }

    @Test
    public void assertHasText() {
        exception.expect(AssertUtils.AssertionException.class);
        exception.expectMessage("someErrorMessage");

        AssertUtils.assertHasText(" ", "someErrorMessage");
    }
}
