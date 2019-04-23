package com.shyfay.product.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

/**
 * @author mx
 * @since 2019/4/23
 */
@Data
@Component
@ConfigurationProperties(prefix="girl")
@RefreshScope
public class GirlConfig {
    private String name;
    private Integer age;
}
