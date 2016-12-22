package com.palmlink.core.collection;

import java.util.Date;
import org.apache.commons.lang.StringUtils;
import com.palmlink.core.collection.converter.DateConverter;
import com.palmlink.core.collection.converter.NumberConverter;

public class TypeConverter {

    @SuppressWarnings({ "unchecked", "rawtypes" })
    public static <T> T fromString(String textValue, Class<T> targetClass) {
        if (targetClass.isPrimitive())
            throw new IllegalArgumentException("目标类型请使用包装类型");

        if (String.class.equals(targetClass))
            return (T) textValue;
        if (StringUtils.isBlank(textValue))
            return null;

        if (Boolean.class.equals(targetClass))
            return (T) Boolean.valueOf(textValue);

        if (Number.class.isAssignableFrom(targetClass))
            return (T) NumberConverter.convertToNumber(textValue, (Class<? extends Number>) targetClass);

        if (Character.class.equals(targetClass))
            return (T) Character.valueOf(textValue.charAt(0));

        if (Enum.class.isAssignableFrom(targetClass))
            return (T) Enum.valueOf((Class<Enum>) targetClass, textValue);
        if (Date.class.equals(targetClass))
            return (T) DateConverter.fromString(textValue);

        throw new TypeConversionException("不支持的类型, 目标类型为=" + targetClass.getName());
    }

    @SuppressWarnings("rawtypes")
    public static String toString(Object value) {
        if (value == null)
            return "";
        if (value instanceof String)
            return (String) value;
        if (value instanceof Boolean)
            return String.valueOf(value);
        if (value instanceof Number)
            return String.valueOf(value);
        if (value instanceof Enum)
            return ((Enum) value).name();
        if (value instanceof Character)
            return String.valueOf(value);
        if (value instanceof Date)
            return DateConverter.toString((Date) value);

        throw new TypeConversionException("不支持的类型, 目标类型为=" + value.getClass().getName());
    }
}
