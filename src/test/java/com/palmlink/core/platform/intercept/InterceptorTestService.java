package com.palmlink.core.platform.intercept;

import org.springframework.stereotype.Service;


/**
 * @author Shihai.Fu
 */
@Service
public class InterceptorTestService {
    private boolean intercepted;

    @InterceptorTestAnnotation
    public void execute() {

    }

    public boolean isIntercepted() {
        return intercepted;
    }

    public void setIntercepted(boolean intercepted) {
        this.intercepted = intercepted;
    }
}
