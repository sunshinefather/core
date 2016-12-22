package com.palmlink.core.platform.intercept;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

/**
 * @author Shihai.Fu
 */
public class TestInterceptor implements MethodInterceptor {
    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        InterceptorTestAnnotation annotation = invocation.getMethod().getAnnotation(InterceptorTestAnnotation.class);
        if (annotation != null) {
            InterceptorTestService service = (InterceptorTestService) invocation.getThis();
            service.setIntercepted(true);
        }
        return invocation.proceed();
    }
}
