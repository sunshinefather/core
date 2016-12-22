package com.palmlink.core.platform.monitor.web;

import com.palmlink.core.platform.SpringObjectFactory;
import com.palmlink.core.platform.monitor.Pass;
import com.palmlink.core.platform.monitor.ServiceMonitor;
import com.palmlink.core.platform.monitor.Status;
import com.palmlink.core.platform.web.rest.FrameWorkRestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @author Shihai.Fu
 */
@Controller
@Pass
public class StatusController extends FrameWorkRestController {
    private SpringObjectFactory objectFactory;

    @RequestMapping(value = "/monitor/status", produces = "application/xml", method = RequestMethod.GET)
    @ResponseBody
    public String status() {
        Status status = new Status();
        List<ServiceMonitor> monitors = objectFactory.getBeans(ServiceMonitor.class);
        status.check(monitors);
        return status.toXML();
    }

    @Autowired
    public void setObjectFactory(SpringObjectFactory objectFactory) {
        this.objectFactory = objectFactory;
    }
}
