package com.palmlink.core.http;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.Consts;
import org.apache.http.NameValuePair;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.message.BasicNameValuePair;

/**
 * Delete request
 * 
 * @author Shihai.Fu
 * 
 */
public class Delete extends HTTPRequest {

    private final List<NameValuePair> parameters = new ArrayList<NameValuePair>();

    public Delete(String url) {
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
        return new HttpDelete(generateURL());
    }

    @Override
    protected void logRequestParams() {
        if (parameters != null)
            for (NameValuePair parameter : parameters) {
                logger.debug("[param] " + parameter.getName() + "=" + parameter.getValue());
            }
    }

    /**
     * generate url with encoded parameters
     * 
     * @return
     */
    String generateURL() {
        if (parameters.isEmpty())
            return url;
        String queryChar = url.contains("?") ? "&" : "?";
        return url + queryChar + URLEncodedUtils.format(parameters, Consts.ISO_8859_1);

    }

}
