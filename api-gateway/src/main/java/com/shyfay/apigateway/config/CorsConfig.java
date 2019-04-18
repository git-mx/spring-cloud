package com.shyfay.apigateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.Arrays;

/**
 * 网关跨域
 * @author mx
 * @since 2019/4/18
 */
@Configuration
public class CorsConfig {
    @Bean
    public CorsFilter corsFilter() {
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        final CorsConfiguration config = new CorsConfiguration();
        //设置允许Cookie跨域
        config.setAllowCredentials(true);
        //设置允许跨域的原始域，例如wwww.a.com，www.b.com
        config.setAllowedOrigins(Arrays.asList("*"));
        //设置允许跨域的请求头
        config.setExposedHeaders(Arrays.asList("x---with, content-type, accept, origin, X-CERT-SysUser-Token,X-Requested-With, utoken, appToken,sessionId"));
        //设置允许跨域的方法
        config.setAllowedMethods(Arrays.asList("POST", "GET", "OPTIONS"));
        //设置缓存时间，也就是说对于相同的跨域请求，就可以这个时间段内都不再进行检查
        config.setMaxAge(3600L);
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }
}
