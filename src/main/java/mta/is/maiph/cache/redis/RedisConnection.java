package mta.is.maiph.cache.redis;

import mta.is.maiph.config.RedisConfig;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 *
 * @author MaiPH
 */
public class RedisConnection {

    private JedisPool jedisPool;
//    private Jedis jedis;

    private static RedisConnection instance;

    private RedisConnection() {
        this.jedisPool = new JedisPool(RedisConfig.HOST, RedisConfig.PORT);
//        jedis = new Jedis(RedisConfig.HOST, RedisConfig.PORT);
    }

    public static RedisConnection instance() {
        if (instance == null) {
            instance = new RedisConnection();
        }
        return instance;
    }

    public Jedis getResource() {
        return jedisPool.getResource();
//        return jedis;
    }
}
