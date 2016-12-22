package com.palmlink.core.http;

import java.io.UnsupportedEncodingException;

import org.apache.http.Consts;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.AbstractHttpEntity;
import org.apache.http.entity.StringEntity;

/**
 * Put request that send data in text
 * 
 * @author Shihai.Fu
 * 
 */
public class TextPut extends HTTPRequest {

    private static final String CHARSET_PARAM = "; charset=";

    private String body;
    private String contentType = HTTPConstants.CONTENT_TYPE_PLAIN;
    private boolean chunked;

    public TextPut(String url) {
        super(url);
    }

    @Override
    HttpRequestBase createRequest() throws UnsupportedEncodingException {
        HttpPut put = new HttpPut(url);
        AbstractHttpEntity entity = new StringEntity(body, Consts.UTF_8);
        entity.setContentType(contentType + CHARSET_PARAM + Consts.UTF_8);
        entity.setChunked(chunked);
        put.setEntity(entity);
        return put;
    }

    @Override
    protected void logRequestParams() {
        logger.debug("contentType={}", contentType);
        logger.debug("[param] body={}", body);
    }

    public void setBody(String body) {
        this.body = body;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public void setChunked(boolean chunked) {
        this.chunked = chunked;
    }
}
