package com.palmlink.core.platform.monitor.web;

import com.palmlink.core.platform.monitor.ExceptionStatistic;
import com.palmlink.core.platform.monitor.Pass;
import com.palmlink.core.platform.web.rest.FrameWorkRestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@Pass
public class ExceptionController extends FrameWorkRestController {
    private ExceptionStatistic exceptionStatistic;

    @RequestMapping(value = "/monitor/exceptions", produces = "application/xml", method = RequestMethod.GET)
    @ResponseBody
    public String exceptionStatistic() {
        return exceptionStatistic.toXML();
    }

    @Autowired
    public void setExceptionStatistic(ExceptionStatistic exceptionStatistic) {
        this.exceptionStatistic = exceptionStatistic;
    }
}
