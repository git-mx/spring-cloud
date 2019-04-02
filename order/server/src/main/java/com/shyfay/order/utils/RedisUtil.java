package com.shyfay.order.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Component;

/**
 * @author mx
 * @since 2019/4/2
 */
@Component
@Slf4j
public class RedisUtil {

    @Autowired
    private StringRedisTemplate redisTemplate;

    public String get(final String key) {
        Object result = null;
        try {
            result = redisTemplate.execute(new RedisCallback<Object>() {
                @Override
                public Object doInRedis(RedisConnection connection) throws DataAccessException {
                    StringRedisSerializer serializer = new StringRedisSerializer();
                    byte[] data = connection.get(serializer.serialize(key));
                    connection.close();
                    if (data == null) {
                        return null;
                    }
                    return serializer.deserialize(data);
                }
            });
        } catch (Exception e) {
            log.error("从redis获取值失败 : {}", e);
        }
        return result != null ? result.toString() : null;
    }

    public boolean set(final String key, final String value){
        Object result = null;
        try {
            result = redisTemplate.execute(new RedisCallback<Object>() {
                @Override
                public Object doInRedis(RedisConnection connection) throws DataAccessException {
                    StringRedisSerializer serializer = new StringRedisSerializer();
                    Boolean success = connection.set(serializer.serialize(key), serializer.serialize(value));
                    connection.close();
                    return success;
                }
            });
        } catch (Exception e) {
            log.error("设置redis的值失败 : {}", e);
        }
        return result != null ? (Boolean) result : false;
    }

    public void del(final String key){
        redisTemplate.delete(key);
    }

    private boolean setNX(final String key, final String value) {
        Object result = null;
        try {
            result = redisTemplate.execute(new RedisCallback<Object>() {
                @Override
                public Object doInRedis(RedisConnection connection) throws DataAccessException {
                    StringRedisSerializer serializer = new StringRedisSerializer();
                    Boolean success = connection.setNX(serializer.serialize(key), serializer.serialize(value));
                    connection.close();
                    return success;
                }
            });
        } catch (Exception e) {
            log.error("Redis执行setNX失败 : {}", e);
        }
        return result != null ? (Boolean) result : false;
    }

    private String getSet(final String key, final String value) {
        Object result = null;
        try {
            result = redisTemplate.execute(new RedisCallback<Object>() {
                @Override
                public Object doInRedis(RedisConnection connection) throws DataAccessException {
                    StringRedisSerializer serializer = new StringRedisSerializer();
                    byte[] ret = connection.getSet(serializer.serialize(key), serializer.serialize(value));
                    connection.close();
                    return serializer.deserialize(ret);
                }
            });
        } catch (Exception e) {
            log.error("Redis执行getSet失败 : {}", key);
        }
        return result != null ? (String) result : null;
    }

    public boolean lock(final String key, int expireTime, int timeoutTime){
        int timeout = timeoutTime;
        while (timeout >= 0) {
            long newExpireTime = System.currentTimeMillis() + expireTime + 1;
            String newExpireStr = String.valueOf(newExpireTime);
            if (this.setNX(key, newExpireStr)) {
                return true;
            }

            String currentValue = this.get(key);
            if (currentValue != null && Long.parseLong(currentValue) < System.currentTimeMillis()) {


                String oldValue = this.getSet(key, newExpireStr);

                if (oldValue != null && oldValue.equals(currentValue)) {
                    return true;
                }
            }
            timeout -= 100;
        }
        log.info("timeout:{},currentValue:{},sysytemTime:{}", timeout, this.get(key), System.currentTimeMillis());
        return false;
    }
}

