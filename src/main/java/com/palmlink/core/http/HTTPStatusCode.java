package com.palmlink.core.http;

/**
 * Encapsulate the http status and related operations
 * 
 * @author Shihai.Fu
 */
public class HTTPStatusCode {

    /**
     * the http status code
     */
    private final int statusCode;

    public HTTPStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public int statusCode() {
        return statusCode;
    }

    /**
     * check if it's a redirect response
     * 
     * @return
     */
    public boolean isRedirect() {
        return statusCode == HTTPConstants.SC_MOVED_TEMPORARILY || statusCode == HTTPConstants.SC_MOVED_PERMANENTLY || statusCode == HTTPConstants.SC_TEMPORARY_REDIRECT || statusCode == HTTPConstants.SC_SEE_OTHER;
    }

    /**
     * check if it's a succeed response
     * 
     * @return
     */
    public boolean isSuccess() {
        return statusCode >= HTTPConstants.SC_OK && statusCode <= HTTPConstants.SC_MULTI_STATUS;
    }

    /**
     * check if it's a error response
     * 
     * @return
     */
    public boolean isServerError() {
        return statusCode >= HTTPConstants.SC_INTERNAL_SERVER_ERROR && statusCode <= HTTPConstants.SC_INSUFFICIENT_STORAGE;
    }

    @Override
    public String toString() {
        return String.valueOf(statusCode);
    }
}
