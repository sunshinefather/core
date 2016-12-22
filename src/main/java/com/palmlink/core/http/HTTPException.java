package com.palmlink.core.http;

/**
 * @author Shihai.Fu
 */
public class HTTPException extends RuntimeException {
    public HTTPException(String message) {
        super(message);
    }

    public HTTPException(Throwable cause) {
        super(cause);
    }
}
