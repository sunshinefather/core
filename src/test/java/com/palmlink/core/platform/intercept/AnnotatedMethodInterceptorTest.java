package com.palmlink.core.platform.intercept;

import com.palmlink.core.SpringTest;
import org.junit.Assert;
import org.junit.Test;

import javax.inject.Inject;

/**
 * @author Shihai.Fu
 * @see com.palmlink.core.TestAppConfig
 */
public class AnnotatedMethodInterceptorTest extends SpringTest {
    @Inject
    InterceptorTestService testService;

    // interceptor registered on TestAppConfig
    @Test
    public void interceptAnnotatedMethod() {
        testService.execute();

        Assert.assertTrue(testService.isIntercepted());
    }
}
