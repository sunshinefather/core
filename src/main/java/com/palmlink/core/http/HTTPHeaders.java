package com.palmlink.core.http;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpRequestBase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Common http headers encapsulation
 * 
 * @author Shihai.Fu
 */
public class HTTPHeaders {

    private final Logger logger = LoggerFactory.getLogger(HTTPHeaders.class);

    /**
     * the name of the header can be duplicated according to HTTP Specification,
     * use List instead of Map
     */
    List<HTTPHeader> headers = new ArrayList<HTTPHeader>();

    /**
     * load http headers from httpResponse, and mark the headers unmodifiable
     * 
     * @param response
     * @return
     */
    static HTTPHeaders loadHeaders(HttpResponse response) {
        Header[] rawHeaders = response.getAllHeaders();
        List<HTTPHeader> httpHeaders = new ArrayList<HTTPHeader>(rawHeaders.length);
        for (Header header : rawHeaders) {
            httpHeaders.add(new HTTPHeader(header.getName(), header.getValue()));
        }

        HTTPHeaders headers = new HTTPHeaders();
        headers.headers = Collections.unmodifiableList(httpHeaders);
        return headers;
    }

    /**
     * add header
     * 
     * @param name
     * @param value
     */
    public void add(String name, String value) {
        headers.add(new HTTPHeader(name, value));
    }

    /**
     * get headers
     * 
     */
    public List<HTTPHeader> getValues() {
        return headers;
    }

    /**
     * get header with name, if multiple values exists, the first one will be
     * returned. null will be returned if the name not exists in the header
     * 
     * @param name
     */
    public String getValue(String name) {
        for (HTTPHeader header : headers) {
            if (header.getName().equals(name))
                return header.getValue();
        }
        return null;
    }

    /**
     * add headers to request
     * 
     * @param request
     */
    void putHeaders(HttpRequestBase request) {
        for (HTTPHeader header : headers) {
            request.addHeader(header.getName(), header.getValue());
        }
    }

    void log() {
        for (HTTPHeader header : headers) {
            logger.debug("[header] {}={}", header.getName(), header.getValue());
        }
    }
}
