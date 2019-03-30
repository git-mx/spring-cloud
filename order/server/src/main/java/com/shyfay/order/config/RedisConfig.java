package com.shyfay.order.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

/**
 * @author mx
 * @since 2019/3/27
 */
@Data
@Component
@ConfigurationProperties(prefix="spring.redis")
@RefreshScope
public class RedisConfig {
    private String host;
    private String password;
}
