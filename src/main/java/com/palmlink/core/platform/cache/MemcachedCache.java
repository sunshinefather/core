package com.palmlink.core.platform.cache;

import java.util.HashSet;
import java.util.Set;
import net.spy.memcached.MemcachedClient;
import org.springframework.cache.Cache;
import org.springframework.cache.support.SimpleValueWrapper;
import com.palmlink.core.util.TimeLength;

public class MemcachedCache implements Cache {
    
    private final String cacheName;
    private final MemcachedClient memcachedClient;
    private final TimeLength expirationTime;

    public MemcachedCache(String cacheName, TimeLength expirationTime, MemcachedClient memcachedClient) {
        this.cacheName = cacheName;
        this.expirationTime = expirationTime;
        this.memcachedClient = memcachedClient;
    }

    @Override
    public String getName() {
        return cacheName;
    }

    @Override
    public Object getNativeCache() {
        return memcachedClient;
    }

    @Override
    public ValueWrapper get(Object key) {
        Object value = memcachedClient.get(constructKey(key));
        if (value == null) return null;
        return new SimpleValueWrapper(value);
    }

    @Override
    public void put(Object key, Object value) {
        if (null == value)return;
        memcachedClient.set(constructKey(key), (int) expirationTime.toSeconds(), value);
        addKeyToCachedKeySet(key);
    }

    private void addKeyToCachedKeySet(Object key) {
        Set<String> keys = fetchKeySet();
        boolean flag=keys.add(constructKey(key));
        if(flag){
            memcachedClient.set(generateKeyOfKeySet(), 0, keys);
        }
    }
    private void removeKeyToCachedKeySet(Object key) {
        Set<String> keys = fetchKeySet();
        boolean flag=keys.remove(constructKey(key));
        if(flag){
            memcachedClient.set(generateKeyOfKeySet(), 0, keys);   
        }
    }

    @SuppressWarnings("unchecked")
    private Set<String> fetchKeySet() {
        Object obj = memcachedClient.get(generateKeyOfKeySet());
        return obj == null ? new HashSet<String>() : (Set<String>) obj;
    }

    @Override
    public void evict(Object key) {
        if (null == key)return;
        memcachedClient.delete(constructKey(key));
        removeKeyToCachedKeySet(key);
    }

    @Override
    public void clear() {
        Set<String> keys = fetchKeySet();
        for (String key : keys){
            memcachedClient.delete(key);
        }
        memcachedClient.delete(generateKeyOfKeySet());
    }
    
    private String constructKey(Object key) {
        return cacheName + ":" + key.toString();
    }
    
    private String generateKeyOfKeySet() {
        return cacheName + "__KEY__";
    }

}
