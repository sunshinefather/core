package com.palmlink.core.soap.axis;

import java.util.Map;

public abstract class AxisRequest {
    
    protected String interfaceName;
    
    protected Map<String, String> parameters;

    public String getInterfaceName() {
        return interfaceName;
    }

    public Map<String, String> getParameterMap() {
        return parameters;
    }
    
    public AxisRequest(String interfaceName, Map<String, String> parameters) {
        this.interfaceName = interfaceName;
        this.parameters = parameters;
    }
    
}