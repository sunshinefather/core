package com.palmlink.core.platform.web.session.provider;

import java.net.SocketAddress;
import java.util.Collection;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Inject;
import net.spy.memcached.CASValue;
import net.spy.memcached.MemcachedClient;
import net.spy.memcached.spring.MemcachedClientFactoryBean;
import net.spy.memcached.ConnectionFactoryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.palmlink.core.platform.monitor.ServiceStatus;
import com.palmlink.core.platform.web.site.SiteSettings;
import com.palmlink.core.util.TimeLength;

public class MemcachedSessionProvider implements SessionProvider {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private static final TimeLength MEMCACHED_TIME_OUT = TimeLength.seconds(3);

    private MemcachedClient memcachedClient;
    
    private SiteSettings siteSettings;
    
    @PostConstruct
    public void initialize() throws Exception {
        MemcachedClientFactoryBean factory = new MemcachedClientFactoryBean();
        factory.setServers(siteSettings.getRemoteSessionServers());
        factory.setOpTimeout(MEMCACHED_TIME_OUT.toMilliseconds());
        factory.setProtocol(ConnectionFactoryBuilder.Protocol.BINARY);
        memcachedClient = (MemcachedClient) factory.getObject();
    }

    @PreDestroy
    public void shutdown() {
        logger.info("shutdown memcached session client");
        memcachedClient.shutdown();
    }

    @Override
    public String getAndRefreshSession(String sessionId) {
        String sessionKey = getCacheKey(sessionId);
        CASValue<Object> cacheValue = memcachedClient.getAndTouch(sessionKey, expirationTime());
        if (cacheValue == null) {
            logger.warn("不能找到会话或者会话已经过期,sessionId:" + sessionId);
            return null;
        }
        return (String) cacheValue.getValue();
    }

    @Override
    public void saveSession(String sessionId, String sessionData) {
        memcachedClient.set(getCacheKey(sessionId), expirationTime(), sessionData);        
    }

    @Override
    public void clearSession(String sessionId) {
        memcachedClient.delete(getCacheKey(sessionId));
    }

    @Override
    public String getServiceName() {
        return "MemcachedSession";
    }

    @Override
    public ServiceStatus getServiceStatus() throws Exception {
        Collection<SocketAddress> availableServers = memcachedClient.getAvailableServers();
        return availableServers.isEmpty() ? ServiceStatus.DOWN : ServiceStatus.UP;
    }
    
    private String getCacheKey(String sessionId) {
        return "session:" + sessionId;
    }
    
    private int expirationTime() {
        return (int) siteSettings.getSessionTimeOut().toSeconds();
    }

    @Inject
    public void setSiteSettings(SiteSettings siteSettings) {
        this.siteSettings = siteSettings;
    }

}