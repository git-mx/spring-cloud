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

    /**
     *
     * 事实证明互联网的redis比局域网的redis要慢很多，都是执行2000条请求，局域网redis
     * 只需要47秒，而外网redis需要110多秒，如果是外网的redis连接，都执行2000条并发请求
     * 这个redis分布式锁可能比JAVA自带的同步synchronized还要慢，但是如果是局域网的redis是肯定比synchronized快很多的。
     * 可能搭建集群和在服务器环境的话，速度会比这个快很多，毕竟我这是在自己的windows个人电脑上做的测试
     *
     */
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

