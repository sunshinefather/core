package com.palmlink.core.platform.exception;

/**
 * @author Shihai.Fu
 */
public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String message) {
        super(message);
    }
}
