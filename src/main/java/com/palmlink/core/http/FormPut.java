package com.palmlink.core.http;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.http.Consts;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.AbstractHttpEntity;
import org.apache.http.message.BasicNameValuePair;

/**
 * Put request with form
 * 
 * @author Shihai.Fu
 * 
 */
public class FormPut extends HTTPRequest {

    private final List<NameValuePair> parameters = new ArrayList<NameValuePair>();

    private boolean chunked;

    public FormPut(String url) {
        super(url);
    }

    /**
     * add parameter
     * 
     * @param key
     * @param value
     */
    public void addParameter(String key, String value) {
        parameters.add(new BasicNameValuePair(key, value));
    }

    /**
     * set the parameter, replace the old value if the key already exists
     * 
     * @param key
     * @param value
     */
    public void setParameter(String key, String value) {
        Iterator<NameValuePair> iterator = parameters.listIterator();
        while (iterator.hasNext()) {
            if (iterator.next().getName().equals(key))
                iterator.remove();
        }
        addParameter(key, value);
    }

    @Override
    HttpRequestBase createRequest() throws UnsupportedEncodingException {
        HttpPut put = new HttpPut(url);
        AbstractHttpEntity entity = new UrlEncodedFormEntity(parameters, Consts.UTF_8);
        entity.setChunked(chunked);
        put.setEntity(entity);
        return put;
    }

    @Override
    protected void logRequestParams() {
        for (NameValuePair parameter : parameters) {
            logger.debug("[param] {}={}", parameter.getName(), parameter.getValue());
        }
    }

    public void setChunked(boolean chunked) {
        this.chunked = chunked;
    }
}
