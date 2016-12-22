package com.palmlink.core.http;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.Consts;
import org.apache.http.NameValuePair;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.message.BasicNameValuePair;

/**
 * Get request
 * 
 * @author Shihai.Fu
 */
public class Get extends HTTPRequest {

    private final List<NameValuePair> parameters = new ArrayList<NameValuePair>();

    public Get(String url) {
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

    @Override
    HttpRequestBase createRequest() {
        return new HttpGet(generateUrl());
    }

    @Override
    protected void logRequestParams() {
        if (parameters != null)
            for (NameValuePair parameter : parameters) {
                logger.debug("[param] {}={}", parameter.getName(), "=" + parameter.getValue());
            }
    }

    String generateUrl() {
        if (parameters.isEmpty())
            return url;
        String queryChar = url.contains("?") ? "&" : "?";
        return url + queryChar + URLEncodedUtils.format(parameters, Consts.ISO_8859_1);
    }
}
