package com.palmlink.core.lock;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.palmlink.core.platform.web.site.LockSettings;
import com.palmlink.core.util.TimeLength;
import net.spy.memcached.ConnectionFactoryBuilder;
import net.spy.memcached.MemcachedClient;
import net.spy.memcached.internal.OperationFuture;
import net.spy.memcached.spring.MemcachedClientFactoryBean;

/**
 * @author chris.qin
 * lockActionName = projectname#classname#methodname
 */
public class DistributeLock {
    
    private final Logger logger = LoggerFactory.getLogger(DistributeLock.class);

    private static final TimeLength MAX_WAIT_TIME =  TimeLength.seconds(180);

    private static final TimeLength SLEEP_TIME =  TimeLength.milliseconds(200);
    
    private static final TimeLength MEMCACHED_TIME_OUT = TimeLength.seconds(3);

    private MemcachedClient memcachedClient;

    private LockSettings lockSettings;
    
    @PostConstruct
    public void initialize() {
        MemcachedClientFactoryBean factory = new MemcachedClientFactoryBean();
        factory.setServers(lockSettings.getRemoteLockServers());
        factory.setOpTimeout(MEMCACHED_TIME_OUT.toMilliseconds());
        factory.setProtocol(ConnectionFactoryBuilder.Protocol.BINARY);
        try {
            memcachedClient = (MemcachedClient) factory.getObject();
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new IllegalStateException(e);
        }
    }
    
    @PreDestroy
    public void shutdown() throws Exception {
        logger.info("shutdown lock client");
        memcachedClient.shutdown();
    }
    
    public boolean lock(LockStack lockStack, String lockActionName)  {
        int tryTimes = 0;
        OperationFuture<Boolean> result = null;
        try {
            do {
                result = memcachedClient.add(generatedLockKey(lockStack), (int) MAX_WAIT_TIME.toSeconds(), lockActionName);
                if (tryTimes > 0) {
                    Thread.sleep(SLEEP_TIME.toMilliseconds()); //sleep 0.2 seconds.
                }
                tryTimes += SLEEP_TIME.toMilliseconds();
                if (tryTimes > MAX_WAIT_TIME.toMilliseconds()) return false; //return false if pass lost lock more than 3 minutes.
            } while(!result.get());
        } catch (Exception e) {
            return false;
        }
        return true;
    } 

    public boolean lock(LockStack lockStack, String lockActionName, int waitTime) {
        int waitTimeMax = waitTime > (int) MAX_WAIT_TIME.toMilliseconds() ?  (int) MAX_WAIT_TIME.toMilliseconds() : waitTime;
        int tryTimes = 0;
        OperationFuture<Boolean> result = null;
        try {
            do {
                result = memcachedClient.add(generatedLockKey(lockStack), (int) TimeLength.minutes(3).toSeconds(), lockActionName);
                if (tryTimes > 0) {
                    Thread.sleep(SLEEP_TIME.toMilliseconds()); //sleep 0.2 seconds.
                }
                tryTimes += SLEEP_TIME.toMilliseconds();
                if (tryTimes > waitTimeMax) return false; //return false if pass 3 mintinues lost lock.
            } while(!result.get());
        } catch (Exception e) {
            return false;
        }
        return true;
    } 
    
    public void unLock(LockStack lockStack) throws Exception {
        memcachedClient.delete(generatedLockKey(lockStack));
    }

    protected String generatedLockKey(LockStack lockStack) {
        return "DISTRIBUTELOCK:" + lockStack.getKey();
    }
    
    @Inject
    public void setLockSettings(LockSettings lockSettings) {
        this.lockSettings = lockSettings;
    }

}