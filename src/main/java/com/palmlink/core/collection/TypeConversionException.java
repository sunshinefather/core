package com.palmlink.core.collection;

/**
 * Exception threw when converting type failed
 * 
 * @author Shihai.Fu
 * 
 */
public class TypeConversionException extends RuntimeException {
    public TypeConversionException(String message) {
        super(message);
    }

    public TypeConversionException(String message, Throwable cause) {
        super(message, cause);
    }
}
