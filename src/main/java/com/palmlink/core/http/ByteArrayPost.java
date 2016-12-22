package com.palmlink.core.http;

import com.palmlink.core.util.DigestUtils;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.ByteArrayEntity;
import java.io.UnsupportedEncodingException;

public class ByteArrayPost extends HTTPRequest {

    /**
     * bytes store the data
     */
    byte[] bytes;

    /**
     * if enable chunked flag, only perform in HTTP 1.1
     */
    boolean chunked;

    public ByteArrayPost(String url) {
        super(url);
    }

    @Override
    HttpRequestBase createRequest() throws UnsupportedEncodingException {
        HttpPost post = new HttpPost(url);
        ByteArrayEntity entity = new ByteArrayEntity(bytes);
        entity.setContentType("binary/octet-stream");
        entity.setChunked(chunked);
        post.setEntity(entity);
        return post;
    }

    @Override
    protected void logRequestParams() {
        logger.debug("[param] bytes=" + DigestUtils.base64(bytes));
    }

    public void setBytes(byte[] bytes) {
        this.bytes = bytes;
    }

    public void setChunked(boolean chunked) {
        this.chunked = chunked;
    }
}
