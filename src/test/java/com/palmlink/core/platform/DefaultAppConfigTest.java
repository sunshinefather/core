package com.palmlink.core.platform;

import com.palmlink.core.SpringTest;
import com.palmlink.core.http.HTTPClient;
import com.palmlink.core.template.FreemarkerTemplate;
import org.junit.Assert;
import org.junit.Test;

import javax.inject.Inject;

/**
 * @author Shihai.Fu
 */
public class DefaultAppConfigTest extends SpringTest {
    @Inject
    HTTPClient httpClient1;

    @Inject
    HTTPClient httpClient2;

    @Test
    public void httpClientShouldBeRegisteredAsPrototype() {
        Assert.assertNotNull(httpClient1);
        Assert.assertNotNull(httpClient2);
        Assert.assertNotSame("httpClient should be prototype", httpClient1, httpClient2);
    }

    @Inject
    FreemarkerTemplate freemarkerTemplate1;

    @Inject
    FreemarkerTemplate freemarkerTemplate2;

    @Test
    public void freemarkerTemplateShouldBeRegisteredAsPrototype() {
        Assert.assertNotNull(freemarkerTemplate1);
        Assert.assertNotNull(freemarkerTemplate2);
        Assert.assertNotSame("freemarkerTemplate should be prototype", freemarkerTemplate1, freemarkerTemplate2);
    }
}
