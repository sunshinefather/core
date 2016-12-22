package com.palmlink.core.platform.service;

public enum EndPoint {
    
    SYSTEM("system"),
    
    SECURITY("security"), 
    
    PRODUCT("product"),
    
    ORDER("order"),
    
    WMS("wms"), 
    
    VENDOR("vendor"),
    
    REPORT("report"),
    
    CUSTOMERCARE("customercare"),
    
    DROPSHIP("dropship");
    
    private String endpoint;
    
    private static final  String  SERVERPOINT = "http://ws.naier.com/";
    
    EndPoint(String endpoint) {
        this.endpoint = endpoint;
    }
    
    public String getEndpoint() {
        return SERVERPOINT + endpoint;
    }
}