package com.palmlink.core.database;

import java.lang.reflect.Method;

import org.springframework.aop.support.StaticMethodMatcherPointcut;

/**
 * DBSwitch Pointcut specifies the classes which marked by @DBSwitch
 * 
 * @author Shihai.Fu
 * 
 */
public class DBSwitchPointcut extends StaticMethodMatcherPointcut {

    @Override
    public boolean matches(Method method, Class<?> targetClass) {
        return null != targetClass.getAnnotation(DBSwitch.class);
    }

}
