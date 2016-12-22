package com.palmlink.core.http;

/**
 * @author Shihai.Fu
 */
public class HTTPHeader {
    private final String name;
    private final String value;

    public HTTPHeader(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }
}
