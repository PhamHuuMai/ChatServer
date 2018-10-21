package mta.is.maiph.cache.redis;

import java.util.Map;
import mta.is.maiph.cache.BaseCache;
import mta.is.maiph.exception.ApplicationException;
import redis.clients.jedis.Jedis;

/**
 *
 * @author MaiPH
 */
public abstract class BaseCacheManager {

    protected RedisConnection connections;

    protected BaseCacheManager() {
        connections = RedisConnection.instance();
    }

    protected abstract String getPrefix();

    private String getKey(String id) {
        return getPrefix() + "_" + id;
    }

    private Jedis getResource() {
        return connections.getResource();
    }

    public BaseCache get(String id) throws ApplicationException{
        String key = getKey(id);
        if (!getResource().exists(key)) {
            Map<String, String> data = initFromDB(id);
            data.forEach((field, value) -> {
                getResource().hset(key, field, value);
            });
            return castToEntity(data);
        }
        return castToEntity(getResource().hgetAll(key));
    }

    public void set(String id, String field, String value) {
        String key = getKey(id);
        getResource().hset(key, field, value);
    }

    protected abstract BaseCache castToEntity(Map<String, String> map);

    protected abstract Map<String, String> initFromDB(String id) throws ApplicationException;
}
