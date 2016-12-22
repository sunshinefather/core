package com.palmlink.core.database.redis;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.util.Assert;

import com.palmlink.core.collection.converter.JSONConverter;

/**
 * utility class to access the redis like common db
 * 
 * @author Shihai.Fu
 * 
 */
public class RedisAccess {

    /**
     * RedisManager to handle redis client operations
     */
    private RedisManager redisManager;

    /**
     * save value with key
     * 
     * @param key
     * @param value
     */
    public <E extends Serializable, T> void save(E key, T value) {
        Assert.notNull(key, "key cannot be null");
        Assert.notNull(value, "value cannot be null");
        redisManager.set(generateKey(value.getClass(), key), JSONConverter.toString(value));
    }

    /**
     * batch save values
     * 
     * @param values
     */
    public <E extends Serializable, T> void batchSave(Map<E, T> values) {
        Map<String, String> keysvalues = new HashMap<String, String>();
        for (Entry<E, T> valueEntry : values.entrySet()) {
            T value = valueEntry.getValue();
            E key = valueEntry.getKey();
            if (key == null || value == null)
                continue;
            keysvalues.put(generateKey(value.getClass(), key.toString()), JSONConverter.toString(value));
        }
        redisManager.pipeLinedset(keysvalues);
    }

    /**
     * 
     * @param targetClass
     * @param key
     * @return
     */
    public <E, T> T get(E key, Class<T> targetClass) {
        String result = redisManager.get(generateKey(targetClass, key));
        if (result == null)
            return null;
        return JSONConverter.fromString(targetClass, result);
    }

    /**
     * batch get with keys
     * 
     * @param targetClass
     * @param keys
     */
    public <E extends Serializable, T> List<T> batchGet(Class<T> targetClass, E... keys) {
        List<String> keyList = new ArrayList<String>();
        List<T> list = new ArrayList<T>();
        for (E key : keys) {
            if (null != key)
                keyList.add(generateKey(targetClass, key));
        }
        List<Object> results = redisManager.pipeLinedGet(keyList);
        for (Object result : results) {
            if (result == null)
                continue;
            list.add(JSONConverter.fromString(targetClass, result.toString()));
        }
        return list;
    }

    public <T, E> void remove(E key, Class<T> targetClass) {
        Assert.notNull(key, "key cannot be null");
        redisManager.del(generateKey(targetClass, key));
    }

    private <T, E> String generateKey(Class<T> targetClass, E id) {
        return targetClass.getSimpleName() + ":" + id;
    }

    public void setRedisManager(RedisManager redisManager) {
        this.redisManager = redisManager;
    }

}
