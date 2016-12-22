package com.palmlink.core.platform.exception;

/**
 * @author Shihai.Fu
 */
public class UserAuthorizationException extends RuntimeException {
    public UserAuthorizationException(String message) {
        super(message);
    }

    public UserAuthorizationException(String message, Throwable cause) {
        super(message, cause);
    }
}
