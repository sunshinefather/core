package com.palmlink.core.platform;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurer;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import com.palmlink.core.platform.cache.CacheProvider;
import com.palmlink.core.platform.cache.CacheRegistry;
import com.palmlink.core.platform.cache.CacheRegistryImpl;
import com.palmlink.core.platform.cache.CacheSettings;
import com.palmlink.core.platform.cache.DefaultCacheKeyGenerator;
import com.palmlink.core.platform.cache.EhcacheCacheManager;
import com.palmlink.core.platform.cache.MemcachedCacheManager;

public abstract class DefaultCacheConfig implements CachingConfigurer {
    
    public abstract CacheSettings cacheSettings();
    
    @Override
    @Bean
    public CacheManager cacheManager() {
        CacheManager cacheManager = createCacheManager(cacheSettings());
        addCaches(new CacheRegistryImpl(cacheManager));
        return cacheManager;
    }

    private CacheManager createCacheManager(CacheSettings settings) {
         CacheProvider provider = settings.getCacheProvider();
         if (CacheProvider.MEMCACHED.equals(provider)){
             MemcachedCacheManager cacheManager = new MemcachedCacheManager(settings.getRemoteCacheServers());
             return cacheManager;
         }else if (CacheProvider.EHCACHE.equals(provider)) {
             return new EhcacheCacheManager();
         }else if(CacheProvider.REDIS.equals(provider)){
             //TODO
             throw new IllegalStateException("not supported cache provider, provider=" +provider);
         }
         throw new IllegalStateException("not supported cache provider, provider=" +provider);
    }

    @Override
    @Bean
    public KeyGenerator keyGenerator() {
        return new DefaultCacheKeyGenerator();
    }
    
    protected abstract void addCaches(CacheRegistry registry);

}