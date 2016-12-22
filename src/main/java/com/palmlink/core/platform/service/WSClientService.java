package com.palmlink.core.platform.service;

import java.util.Locale;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.palmlink.core.http.Delete;
import com.palmlink.core.http.Get;
import com.palmlink.core.http.HTTPBinaryResponse;
import com.palmlink.core.http.HTTPClient;
import com.palmlink.core.http.HTTPConstants;
import com.palmlink.core.http.HTTPRequest;
import com.palmlink.core.http.HTTPResponse;
import com.palmlink.core.http.HTTPStatusCode;
import com.palmlink.core.http.TextPost;
import com.palmlink.core.http.TextPut;
import com.palmlink.core.json.JSONBinder;
import com.palmlink.core.platform.exception.WsClientInValidateException;
import com.palmlink.core.platform.web.rest.ErrorResponse;

public final class WSClientService {

    private final Logger logger = LoggerFactory.getLogger(WSClientService.class);

    private static final String ATTRIBUTE_CLIENT_KEY = "WS-APPKEY";
    private static final String LOCALE = "Accept-Language";
    private Locale locale;

    public <T> T post(EndPointBuilder<T> builder) {
        HTTPClient httpClient = createHTTPClient();
        TextPost post = new TextPost(builder.getService());
        post.setAccept(HTTPConstants.CONTENT_TYPE_JSON);
        post.setContentType(HTTPConstants.CONTENT_TYPE_JSON);
        post.addHeader(ATTRIBUTE_CLIENT_KEY, builder.getAppKey());
        addLocaleHeader(post);
        if (!StringUtils.isBlank(builder.getBodyContent())) {
            post.setBody(builder.getBodyContent());
        }
        return convertResponse(builder, httpClient.execute(post));
    }

    public <T> T put(EndPointBuilder<T> builder) {
        HTTPClient httpClient = createHTTPClient();
        TextPut put = new TextPut(builder.getService());
        put.setAccept(HTTPConstants.CONTENT_TYPE_JSON);
        put.setContentType(HTTPConstants.CONTENT_TYPE_JSON);
        put.addHeader(ATTRIBUTE_CLIENT_KEY, builder.getAppKey());
        addLocaleHeader(put);
        if (!StringUtils.isBlank(builder.getBodyContent())) {
            put.setBody(builder.getBodyContent());
        }
        return convertResponse(builder, httpClient.execute(put));
    }

    public <T> T get(EndPointBuilder<T> builder) {
        HTTPClient httpClient = createHTTPClient();
        Get get = new Get(builder.getService());
        get.setAccept(HTTPConstants.CONTENT_TYPE_JSON);
        get.addHeader(ATTRIBUTE_CLIENT_KEY, builder.getAppKey());
        addLocaleHeader(get);
        return convertResponse(builder, httpClient.execute(get));
    }

    public <T> T delete(EndPointBuilder<T> builder) {
        HTTPClient httpClient = createHTTPClient();
        Delete delete = new Delete(builder.getService());
        delete.setAccept(HTTPConstants.CONTENT_TYPE_JSON);
        delete.addHeader(ATTRIBUTE_CLIENT_KEY, builder.getAppKey());
        addLocaleHeader(delete);
        return convertResponse(builder, httpClient.execute(delete));
    }

    public <T> HTTPBinaryResponse download(EndPointBuilder<T> builder) {
        HTTPClient httpClient = createHTTPClient();
        TextPost post = new TextPost(builder.getService());
        post.setAccept(HTTPConstants.CONTENT_TYPE_JSON);
        post.setContentType(HTTPConstants.CONTENT_TYPE_JSON);
        addLocaleHeader(post);
        if (!StringUtils.isBlank(builder.getBodyContent())) {
            post.setBody(builder.getBodyContent());
        }
        return httpClient.download(post);
    }

    public <T> T convertResponse(EndPointBuilder<T> builder, HTTPResponse response) {
        HTTPStatusCode statusCode = response.getStatusCode();
        logger.debug("response status code => {}", statusCode.statusCode());
        String responseText = response.getResponseText();
        if (!statusCode.isSuccess()) {
            throw new WsClientInValidateException("Failed to call api service, responseText=" + responseText + ", statusCode=" + statusCode.statusCode());
        }
        validateResponse(responseText);
        return JSONBinder.binder(builder.getResponseClass(), builder.getElementClasses()).fromJSON(responseText);
    }

    public void validateResponse(String responseText) {
        if (responseText.startsWith("{\"status\":\"FAILED\"")) {
            ErrorResponse errorResponse = JSONBinder.binder(ErrorResponse.class).fromJSON(responseText);
            String error = errorResponse.getMessage();
            throw new WsClientInValidateException(error);
        }
    }

    private HTTPClient createHTTPClient() {
        HTTPClient httpClient = new HTTPClient();
        httpClient.setValidateStatusCode(false);
        return httpClient;
    }

    // set locale
    private void addLocaleHeader(HTTPRequest request) {
        request.addHeader(LOCALE, locale.getLanguage() + "-" + locale.getCountry());
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
    }

}
