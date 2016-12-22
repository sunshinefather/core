package com.palmlink.core.http;

import org.apache.http.client.methods.HttpRequestBase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.IOException;

public abstract class HTTPRequest {

    protected final Logger logger = LoggerFactory.getLogger(getClass());

    protected final String url;

    private final HTTPHeaders headers = new HTTPHeaders();

    protected HTTPRequest(String url) {
        this.url = url;
    }

    public void addHeader(String name, String value) {
        headers.add(name, value);
    }

    public void setAccept(String contentType) {
        headers.add(HTTPConstants.HEADER_ACCEPT, contentType);
    }

    HttpRequestBase create() throws IOException {
        HttpRequestBase request = createRequest();
        headers.putHeaders(request);
        return request;
    }

    /**
     * log the request
     */
    void logRequest() {
        logger.debug("====== http request begin ======");
        headers.log();
        logRequestParams();
        logger.debug("====== http request end ======");
    }

    /**
     * sub class should implement this method to complete the request creation
     * 
     * @throws IOException
     */
    abstract HttpRequestBase createRequest() throws IOException;

    /**
     * sub class should implements this method to complete logging the request
     * parameters
     */
    protected abstract void logRequestParams();
}
