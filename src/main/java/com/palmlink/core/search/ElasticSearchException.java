package com.palmlink.core.search;

public class ElasticSearchException extends RuntimeException {
    public ElasticSearchException(String message) {
        super(message);
    }

    public ElasticSearchException(String message, Throwable cause) {
        super(message, cause);
    }
}