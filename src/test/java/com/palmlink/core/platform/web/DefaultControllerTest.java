package com.palmlink.core.platform.web;

import com.palmlink.core.SpringTest;
import org.junit.Assert;
import org.junit.Test;

import javax.inject.Inject;

/**
 * @author Shihai.Fu
 */
public class DefaultControllerTest extends SpringTest {
    @Inject
    TestController testController;

    @Test
    public void getMessage() {
        String message = testController.getMessage("test.message1");
        Assert.assertEquals("testMessage", message);
    }

    @Test
    public void getMessageWithParams() {
        String message = testController.getMessage("test.message2", "Value");
        Assert.assertEquals("testMessageValue", message);
    }
}
