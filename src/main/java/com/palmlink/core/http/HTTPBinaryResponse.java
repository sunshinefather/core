package com.palmlink.core.http;

/**
 * Response that store the data in binary array
 * 
 * @author Shihai.Fu
 */
public class HTTPBinaryResponse {

    /**
     * response status code
     */
    private final HTTPStatusCode statusCode;
    /**
     * response headers
     */
    private final HTTPHeaders headers;
    /**
     * binary content
     */
    private final byte[] responseContent;

    public HTTPBinaryResponse(HTTPStatusCode statusCode, HTTPHeaders headers, byte[] responseContent) {
        this.headers = headers;
        this.statusCode = statusCode;
        this.responseContent = responseContent;
    }

    public byte[] getResponseContent() {
        return responseContent;
    }

    public HTTPStatusCode getStatusCode() {
        return statusCode;
    }

    public HTTPHeaders getHeaders() {
        return headers;
    }
}
