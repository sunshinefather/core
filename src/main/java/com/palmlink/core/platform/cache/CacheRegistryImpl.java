package com.palmlink.core.platform.cache;

import org.springframework.cache.CacheManager;
import net.sf.ehcache.config.CacheConfiguration;
import com.palmlink.core.util.TimeLength;
import net.sf.ehcache.Cache;
//TODO未处理redis缓存
public class CacheRegistryImpl implements CacheRegistry {

    private final CacheManager cacheManager;

    public CacheRegistryImpl(CacheManager cacheManager) {
        this.cacheManager = cacheManager;
    }

    @Override
    public void addCache(String cacheName, TimeLength expirationTime) {
        if (cacheManager instanceof MemcachedCacheManager) {
            ((MemcachedCacheManager) cacheManager).add(cacheName, expirationTime);
        } else {
            addCache(cacheName, expirationTime, 0);
        }
    }

    @Override
    public void addCache(String cacheName, TimeLength expirationTime, int maxEntriesInHeap) {
        if(cacheManager instanceof EhcacheCacheManager){
            CacheConfiguration cacheConfiguration = new CacheConfiguration(cacheName, maxEntriesInHeap);
            cacheConfiguration.setTimeToLiveSeconds(expirationTime.toSeconds());
            ((EhcacheCacheManager) cacheManager).addCache(new Cache(cacheConfiguration));
        }
    }

}