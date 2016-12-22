package com.palmlink.core.database.redis;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.Pipeline;

/**
 * Redis client using JedisPool
 * 
 * @author Shihai.Fu
 * 
 */
public class RedisManager {

    private final Logger logger = LoggerFactory.getLogger(RedisManager.class);

    private JedisPool jedisPool;

    /**
     * set key-value
     * 
     * @param key
     * @param value
     */
    public String set(String key, String value) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.set(key, value);
        } finally {
            if (null != jedis) {
                jedisPool.returnResource(jedis);
                logger.debug("set, key={}, value={}", new Object[] { key, value });
            }
        }
    }

    /**
     * batch set without transaction, using pipeLine to improve the performance
     * 
     * @param keysvalues
     */
    public void pipeLinedset(Map<String, String> keysvalues) {
        if (keysvalues == null || keysvalues.isEmpty())
            return;
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            Pipeline p = jedis.pipelined();
            for (Entry<String, String> value : keysvalues.entrySet())
                p.set(value.getKey(), value.getValue());
            p.sync();
        } finally {
            if (null != jedis) {
                jedisPool.returnResource(jedis);
                logger.debug("pipeLine set, keysvalues=", new Object[] { keysvalues });
            }
        }
    }

    /**
     * get value with key
     * 
     * @param key
     */
    public String get(String key) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.get(key);
        } finally {
            if (null != jedis) {
                jedisPool.returnResource(jedis);
                logger.debug("get, key={}", new Object[] { key });
            }
        }
    }

    /**
     * batch get with pipeLine
     * 
     * @param keys
     */
    public List<Object> pipeLinedGet(List<String> keys) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            Pipeline p = jedis.pipelined();
            for (String key : keys)
                p.get(key);
            List<Object> results = p.syncAndReturnAll();
            p.sync();
            return results;
        } finally {
            if (null != jedis) {
                jedisPool.returnResource(jedis);
                logger.debug("pipeLine get, keys=", new Object[] { keys });
            }
        }
    }

    /**
     * check if key exists
     * 
     * @param key
     */
    public boolean exist(String key) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.exists(key);
        } finally {
            if (null != jedis) {
                jedisPool.returnResource(jedis);
                logger.debug("exist, key={}", new Object[] { key });
            }
        }
    }

    /**
     * delete the key
     * 
     * @param key
     */
    public Long del(String key) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.del(key);
        } finally {
            if (null != jedis) {
                jedisPool.returnResource(jedis);
                logger.debug("del, key={}", new Object[] { key });
            }
        }
    }

    /**
     * increment by 1
     * 
     * @param key
     */
    public Long increment(String key) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.incr(key);
        } finally {
            if (null != jedis) {
                jedisPool.returnResource(jedis);
                logger.debug("increment, key={}", new Object[] { key });
            }
        }
    }

    /**
     * increment by value
     * 
     * @param key
     */
    public Long incrementBy(String key, long value) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            jedis.incrBy(key, value);
            return jedis.incr(key);
        } finally {
            if (null != jedis) {
                jedisPool.returnResource(jedis);
                logger.debug("increment by, key={}, by={}", new Object[] { key, value });
            }
        }
    }

    /**
     * add values to the sorted set
     * 
     * @param key
     * @param scoreMembers
     */
    public Long zadd(String key, Map<String, Double> scoreMembers) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.zadd(key, scoreMembers);
        } finally {
            if (null != jedis) {
                jedisPool.returnResource(jedis);
                logger.debug("zadd, key={}, scoreMembers={}", new Object[] { key, scoreMembers });
            }
        }
    }

    /**
     * get the range data from the sorted set
     * 
     * @param key
     * @param start
     * @param end
     */
    public Set<String> zrange(String key, long start, long end) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.zrange(key, start, end);
        } finally {
            if (null != jedis) {
                jedisPool.returnResource(jedis);
                logger.debug("zrange, key={}, start={}, end={}", new Object[] { key, start, end });
            }
        }
    }

    /**
     * get the size of the sorted set
     * 
     * @param key
     * @param start
     * @param end
     */
    public Long zcard(String key) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();

            return jedis.zcard(key);
        } finally {
            if (null != jedis) {
                jedisPool.returnResource(jedis);
                logger.debug("zcard, key={}", new Object[] { key });
            }
        }
    }

    /**
     * remove values from the sorted set
     * 
     * @param key
     * @param start
     * @param end
     */
    public Long zrem(String key, String... members) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.zrem(key, members);
        } finally {
            if (null != jedis) {
                jedisPool.returnResource(jedis);
                logger.debug("zrem, key={}, members={}", new Object[] { key, members });
            }
        }
    }

    public void setJedisPool(JedisPool jedisPool) {
        this.jedisPool = jedisPool;
    }

}
