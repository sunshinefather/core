package com.palmlink.core.platform;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;

/**
 * @author Shihai.Fu
 */
@Repository
public class CacheTestRepository {
    @Cacheable("entities")
    public Entity getEntityById(String id) {
        return new Entity(id);
    }

    public static class Entity {
        private final String id;

        public Entity(String id) {
            this.id = id;
        }

        public String getId() {
            return id;
        }
    }
}
