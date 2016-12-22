package com.palmlink.core.platform.exception;

/**
 * @author Shihai.Fu
 */
public class InvalidRequestException extends RuntimeException {
    private String field;

    public InvalidRequestException(String message) {
        this(null, message, null);
    }

    public InvalidRequestException(String field, String message) {
        this(field, message, null);
    }

    public InvalidRequestException(String field, String message, Throwable cause) {
        super(message, cause);
        this.field = field;
    }

    public String getField() {
        return field;
    }
}
