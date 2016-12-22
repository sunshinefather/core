package com.palmlink.core.platform.web.form;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Map;

/**
 * @author Shihai.Fu
 */
public class AnnotationFormArgumentResolverTest {
    @Form
    public static class TestForm {
        @FormParam("input1")
        private String field1;
        @FormParam("input2")
        private String field2;

        public String getField1() {
            return field1;
        }

        public void setField1(String field1) {
            this.field1 = field1;
        }

        public String getField2() {
            return field2;
        }

        public void setField2(String field2) {
            this.field2 = field2;
        }
    }

    AnnotationFormArgumentResolver resolver;

    @Before
    public void createAnnotationFormArgumentResolver() {
        resolver = new AnnotationFormArgumentResolver();
    }

    @Test
    public void getOrCreateParameterMappings() {
        Map<String, String> mappings = resolver.getOrCreateParameterMappings(TestForm.class);
        Assert.assertEquals(2, mappings.size());
        Assert.assertEquals("field1", mappings.get("input1"));
        Assert.assertEquals("field2", mappings.get("input2"));
    }
}
