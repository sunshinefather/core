package com.palmlink.core.http;

import java.io.UnsupportedEncodingException;

import org.apache.http.Consts;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.AbstractHttpEntity;
import org.apache.http.entity.StringEntity;

/**
 * Post request that send the data in text
 * 
 * @author Shihai.Fu
 */
public class TextPost extends HTTPRequest {

    private static final String CHARSET_PARAM = "; charset=";

    private String body;
    private String contentType = HTTPConstants.CONTENT_TYPE_PLAIN;
    boolean chunked;

    public TextPost(String url) {
        super(url);
    }

    @Override
    HttpRequestBase createRequest() throws UnsupportedEncodingException {
        HttpPost post = new HttpPost(url);
        AbstractHttpEntity entity = new StringEntity(body, Consts.UTF_8);
        entity.setContentType(contentType + CHARSET_PARAM + Consts.UTF_8);
        entity.setChunked(chunked);
        post.setEntity(entity);
        return post;
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
