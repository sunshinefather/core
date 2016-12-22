package com.palmlink.core.soap.axis;

import java.rmi.RemoteException;
import java.util.Map;
import java.util.Map.Entry;
import javax.xml.namespace.QName;
import javax.xml.rpc.ServiceException;
import org.apache.axis.client.Call;
import org.apache.axis.client.Service;
import com.palmlink.core.crypto.MD5;

public class AxisClient {
    
    private final String endPoint;
    
    public AxisClient(String endPoint) {
        this.endPoint = endPoint;
    }

    public String soapRequest(AxisRequest request) throws ServiceException, RemoteException {
        Service service = new Service();
        Call call = (Call) service.createCall();
        call.setTargetEndpointAddress(endPoint);
        call.setOperationName(new QName(endPoint, request.getInterfaceName()));
        return (String) call.invoke(new Object[] {createRequestArguments(request.getParameterMap())});
    }

    private String createRequestArguments(Map<String, String> parameter) {
        final StringBuilder arguments = new StringBuilder();
        String inputArgument = createInputArguments(parameter);
        String md5code = encrypt(inputArgument);
        arguments.append(inputArgument).append("&checkValue=").append(md5code); 
        return arguments.toString();
    }

    private String createInputArguments(Map<String, String> parameters) {
        final StringBuilder arguments = new StringBuilder();
        if (!parameters.isEmpty()) {
            int k = 0;
            for (Entry<String, String> entry : parameters.entrySet()) {
                if (k++ > 0) {
                    arguments.append("&");
                }
                arguments.append(entry.getKey() + "=" + entry.getValue());
            }
        }
        return arguments.toString();
    } 
    
    private String encrypt(String source) {
        MD5 md5 = new MD5();
        return md5.encrypt(source);
    }
    
}