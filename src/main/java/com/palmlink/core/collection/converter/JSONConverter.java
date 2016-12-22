package com.palmlink.core.collection.converter;

import com.palmlink.core.json.JSONBinder;

/**
 * JSON converter
 * 
 * @author Shihai.Fu
 * 
 */
public class JSONConverter {

    /**
     * convert JSON String to Object
     * 
     * @param targetClass
     * @param value
     */
    public static <T> T fromString(Class<T> targetClass, String value) {
        return JSONBinder.binder(targetClass).fromJSON(value);
    }

    /**
     * convert Object to JSON String
     * 
     * @param value
     */
    @SuppressWarnings("unchecked")
    public static <T> String toString(T value) {
        return JSONBinder.binder((Class<T>) value.getClass()).toJSON(value);
    }
}
