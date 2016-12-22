package com.palmlink.core.util;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * @author Shihai.Fu
 */
public final class ExceptionUtils {
    public static String stackTrace(Throwable e) {
        StringWriter writer = new StringWriter();
        e.printStackTrace(new PrintWriter(writer));
        return writer.toString();
    }

    private ExceptionUtils() {
    }
}
