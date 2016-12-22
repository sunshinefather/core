package com.palmlink.core.database;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * annotation to mark if the type or method will switch the jdbcAccess to
 * specified switch type
 * 
 * @author Shihai.Fu
 * 
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.TYPE, ElementType.METHOD })
public @interface Switch {

    public SwitchType value() default SwitchType.SLAVE;

    public enum SwitchType {
        MASTER, SLAVE
    }
}
