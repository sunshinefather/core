package com.palmlink.core.platform.monitor;

public interface ServiceMonitor {
    
    ServiceStatus getServiceStatus() throws Exception;

    String getServiceName();
    
}