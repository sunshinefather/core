package com.palmlink.core.collection;

import java.util.Date;
import org.apache.commons.lang.StringUtils;

public final class Key<T> {

    private final String name;

    private final Class<? extends T> targetClass;

    private Key(String name, Class<? extends T> targetClass) {
        if (StringUtils.isBlank(name))
            throw new IllegalArgumentException("name cannot be empty");
        if (targetClass == null)
            throw new IllegalArgumentException("targetClass cannot be null");
        if (targetClass.isPrimitive())
            throw new IllegalArgumentException("targetClass cannot be primitive, use wrapper class instead, e.g. Integer.class for int.class");

        this.name = name;
        this.targetClass = targetClass;
    }

    public static <T> Key<T> key(String name, Class<T> targetClass) {
        return new Key<T>(name, targetClass);
    }

    public static Key<Integer> intKey(String name) {
        return key(name, Integer.class);
    }

    public static Key<Long> longKey(String name) {
        return key(name, Long.class);
    }

    public static Key<Double> doubleKey(String name) {
        return key(name, Double.class);
    }

    public static Key<String> stringKey(String name) {
        return key(name, String.class);
    }


    public static Key<Date> dateKey(String name) {
        return key(name, Date.class);
    }

    public static Key<Boolean> booleanKey(String name) {
        return key(name, Boolean.class);
    }

    public String name() {
        return name;
    }

    public Class<? extends T> targetClass() {
        return targetClass;
    }

    @Override
    public String toString() {
        return String.format("[name=%s, class=%s]", name, targetClass.getName());
    }

    @SuppressWarnings("rawtypes")
    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof Key))
            return false;

        Key that = (Key) o;

        return name.equals(that.name) && targetClass.equals(that.targetClass);
    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + targetClass.hashCode();
        return result;
    }
}