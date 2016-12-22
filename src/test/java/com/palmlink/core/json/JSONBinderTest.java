package com.palmlink.core.json;

import com.palmlink.core.util.DateUtils;
import org.junit.Assert;
import org.junit.Test;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.util.Calendar;
import java.util.Date;

import static org.junit.Assert.assertThat;
import static org.junit.matchers.JUnitMatchers.containsString;

/**
 * @author Shihai.Fu
 */
public class JSONBinderTest {
    @XmlType
    @XmlAccessorType(XmlAccessType.FIELD)
    public static class TestBean {
        @XmlElement(name = "different-field")
        private String field;
        @XmlElement(name = "date")
        private Date date;

        public String getField() {
            return field;
        }

        public void setField(String field) {
            this.field = field;
        }

        public Date getDate() {
            return date;
        }

        public void setDate(Date date) {
            this.date = date;
        }
    }

    @Test
    public void supportJAXBAnnotations() {
        JSONBinder<TestBean> binder = JSONBinder.binder(TestBean.class);
        TestBean bean = new TestBean();
        bean.setField("value");

        String json = binder.toJSON(bean);

        System.err.println(json);

        assertThat(json, containsString("{\"different-field\":\"value\"}"));
    }

    @Test
    public void fromJSON() {
        JSONBinder<TestBean> binder = JSONBinder.binder(TestBean.class);
        TestBean bean = binder.fromJSON("{\"different-field\":\"value\"}");

        Assert.assertEquals("value", bean.getField());
    }

    @Test
    public void serializeDate() {
        JSONBinder<TestBean> binder = JSONBinder.binder(TestBean.class);
        TestBean bean = new TestBean();
        Date date = DateUtils.date(2012, Calendar.APRIL, 18, 11, 30, 0);
        bean.setDate(date);

        String json = binder.toJSON(bean);

        System.err.println(json);

        assertThat(json, containsString("{\"date\":\"18/04/2012 11:30:00\"}"));

        TestBean result = binder.fromJSON(json);

        Assert.assertEquals(date, result.getDate());
    }
}
