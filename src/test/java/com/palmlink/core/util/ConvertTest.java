package com.palmlink.core.util;

import org.junit.Assert;
import org.junit.Test;

import javax.xml.datatype.XMLGregorianCalendar;
import java.util.Calendar;
import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

/**
 * @author Shihai.Fu
 */
public class ConvertTest {
    @Test
    public void toIntShouldReturnDefaultValueWithInvalidFormat() {
        assertEquals(-1, (int) Convert.toInt("", -1));
        assertEquals(-1, (int) Convert.toInt("   ", -1));
        assertEquals(-1, (int) Convert.toInt(null, -1));
        assertEquals(-1, (int) Convert.toInt("a", -1));
    }

    @Test
    public void toInt() {
        assertEquals(10, (int) Convert.toInt("10", -1));
    }

    @Test
    public void toDateShouldReturnDefaultValueWithInvalidFormat() {
        Date date = Convert.toDate("", null);

        assertNull(date);
    }

    @Test
    public void convertToDateWithFormatPattern() {
        Date date = Convert.toDate("01-02-1998", "MM-dd-yyyy", null);

        System.out.println(date);

        assertNotNull(date);

        Calendar calendar = DateUtils.calendar(date);

        assertEquals(2, calendar.get(Calendar.DATE));
        assertEquals(Calendar.JANUARY, calendar.get(Calendar.MONTH));
        assertEquals(1998, calendar.get(Calendar.YEAR));
    }

    @Test
    public void toDate() {
        // before 2000
        Date date = Convert.toDate("02/01/1998", null);

        System.out.println(date);

        assertNotNull(date);

        Calendar calendar = DateUtils.calendar(date);

        assertEquals(2, calendar.get(Calendar.DATE));
        assertEquals(Calendar.JANUARY, calendar.get(Calendar.MONTH));
        assertEquals(1998, calendar.get(Calendar.YEAR));

        // after 2000
        date = Convert.toDate("22/06/2008", null);

        System.out.println(date);

        assertNotNull(date);

        calendar = DateUtils.calendar(date);

        assertEquals(22, calendar.get(Calendar.DATE));
        assertEquals(Calendar.JUNE, calendar.get(Calendar.MONTH));
        assertEquals(2008, calendar.get(Calendar.YEAR));
    }

    @Test
    public void toDateTime() {
        Date date = Convert.toDateTime("02/01/1998 13:10:00", null);

        System.out.println(date);

        assertNotNull(date);

        Calendar calendar = DateUtils.calendar(date);

        assertEquals(2, calendar.get(Calendar.DATE));
        assertEquals(Calendar.JANUARY, calendar.get(Calendar.MONTH));
        assertEquals(1998, calendar.get(Calendar.YEAR));

        assertEquals(13, calendar.get(Calendar.HOUR_OF_DAY));
        assertEquals(10, calendar.get(Calendar.MINUTE));
    }

    @Test
    public void toXMLGregorianCalendar() {
        Date date = DateUtils.date(2000, Calendar.JANUARY, 31, 0, 0, 0);

        XMLGregorianCalendar gregorianCalendar = Convert.toXMLGregorianCalendar(date);

        Assert.assertEquals(2000, gregorianCalendar.getYear());
        Assert.assertEquals(1, gregorianCalendar.getMonth());
        Assert.assertEquals(31, gregorianCalendar.getDay());
    }

    @Test
    public void convertDateToString() {
        Date date = DateUtils.date(2010, Calendar.JULY, 19);
        assertEquals("2010-07-19", Convert.toString(date, "yyyy-MM-dd"));
        assertEquals("20100719", Convert.toString(date, "yyyyMMdd"));
    }
}
