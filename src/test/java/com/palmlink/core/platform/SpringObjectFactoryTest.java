package com.palmlink.core.platform;

import com.palmlink.core.SpringTest;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;

/**
 * @author Shihai.Fu
 */
public class SpringObjectFactoryTest extends SpringTest {
    @Inject
    SpringObjectFactory objectFactory;

    @Service
    public static class TestDependency {

    }

    @Service
    public static class TestBean {
        private TestDependency dependency;

        @Transactional
        public void verify() {
            Assert.assertNotNull(dependency);
        }

        @Inject
        public void setDependency(TestDependency dependency) {
            this.dependency = dependency;
        }
    }

    @Test
    public void initializeBeanShouldApplyAOPInterceptor() {
        TestBean bean = new TestBean();
        TestBean beanProxy1 = objectFactory.initializeBean(bean);
        beanProxy1.verify();
        Assert.assertNotSame(beanProxy1, bean);
        Assert.assertFalse(TestBean.class.equals(beanProxy1.getClass()));
    }

    @Test
    public void initializeBeanShouldCreatePrototype() {
        TestBean bean = new TestBean();
        TestBean beanProxy1 = objectFactory.initializeBean(bean);
        TestBean beanProxy2 = objectFactory.initializeBean(bean);
        Assert.assertNotSame(beanProxy1, beanProxy2);
    }
}
