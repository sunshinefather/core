package com.palmlink.core.platform.cache;

import java.lang.reflect.Method;
import java.util.Date;
import org.springframework.cache.interceptor.KeyGenerator;
import com.alibaba.fastjson.JSON;
import com.palmlink.core.util.Convert;
import com.palmlink.core.util.DigestUtils;

public class DefaultCacheKeyGenerator implements KeyGenerator {

    private static final String NO_PARAM_KEY = "";

    @Override
    public Object generate(Object target, Method method, Object... params) {
        StringBuilder builder = new StringBuilder();
        builder.append(generatorTargetName(target));
        builder.append(generatorMethodName(method));
        builder.append(generatorParamsName(params));
        return encodeKey(builder.toString());
    }

    public String encodeKey(String key) {
        return DigestUtils.md5(key);
    }

    private String generatorParamsName(Object[] params) {
        if (params.length ==0) return NO_PARAM_KEY;
        if (params.length == 1) return getKeyValue(params[0]);
        StringBuilder builder = new StringBuilder();
        for (Object param : params) {
            String value = getKeyValue(param);
            builder.append(value);
            builder.append(':');
        }
        return builder.toString();
    }

    private String getKeyValue(Object param) {
        if (param instanceof CacheKeyGenerator) return ((CacheKeyGenerator) param).buildCacheKey();
        if (param instanceof Enum) return ((Enum<?>) param).name();
        if (param instanceof Date) return Convert.toString((Date) param, Convert.DATE_FORMAT_DATETIME);
        return JSON.toJSONString(param);
    }
    
    private String generatorMethodName(Method method){
        return method.getName();
    }
    
    private String generatorTargetName(Object obj){
        return obj.getClass().getName();
    }
}