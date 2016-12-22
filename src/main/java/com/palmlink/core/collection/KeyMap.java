package com.palmlink.core.collection;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;
import com.palmlink.core.util.RuntimeIOException;

public class KeyMap {

    private final Map<String, Object> context = new HashMap<String, Object>();

    public <T> void put(Key<T> key, T value) {
        context.put(key.name(), value);
    }

    public <T> T get(Key<T> key) {
        return get(key.name(), key.targetClass());
    }

    public <T> String getString(Key<T> key) {
        Object value = context.get(key.name());
        if (value == null)
            return null;
        return TypeConverter.toString(value);
    }

    public void putString(String key, String value) {
        context.put(key, value);
    }

    @SuppressWarnings("unchecked")
    protected <T> T get(String key, Class<T> expectedClass) {
        Object value = context.get(key);

        if (value == null)
            return null;

        if (expectedClass.isAssignableFrom(value.getClass()))
            return (T) value;

        if (!(value instanceof String))
            throw new TypeConversionException("class类型不匹配, 目标class类型=" + value.getClass().getName() + ",预期Class=" + expectedClass.getName());

        return TypeConverter.fromString((String) value, expectedClass);
    }

    public void clear() {
        context.clear();
    }

    public Map<String, Object> getAll() {
        return context;
    }

    public int size() {
        return context.size();
    }

    public String serialize() {
        StringBuilder builder = new StringBuilder();

        for (Map.Entry<String, Object> entry : context.entrySet()) {
            String key = entry.getKey();
            String value = TypeConverter.toString(entry.getValue());
            builder.append(key).append("=").append(value).append("\n");
        }
        return builder.toString();
    }

    public void deserialize(String text) {
        try {
            BufferedReader reader = new BufferedReader(new StringReader(text));
            while (true) {
                String line = reader.readLine();
                if (line == null)
                    break;
                int index = line.indexOf('=');
                if (index <= 0)
                    throw new TypeConversionException("不能解析这一行, 行内容=" + line);
                String key = line.substring(0, index);
                String value = (index == line.length() - 1) ? null : line.substring(index + 1);
                context.put(key, value);
            }
        } catch (IOException e) {
            throw new RuntimeIOException(e);
        }
    }
}