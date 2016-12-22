package com.palmlink.core.collection.converter;

import java.util.Date;
import com.palmlink.core.collection.TypeConversionException;
import com.palmlink.core.util.Convert;

public class DateConverter {

    public static Date fromString(String property) {
        return parseDateTime(property);
    }

    public static String toString(Date value) {
        return Convert.toString(value, Convert.DATE_FORMAT_DATETIME);
    }

    static Date parseDateTime(String textValue) {
        Date date = Convert.toDateTime(textValue, null);
        if (date == null)
            date = Convert.toDate(textValue, null);
        if (date == null)
            throw new TypeConversionException("不能转换为日期类型, 文本内容为=" + textValue);
        return date;
    }
}