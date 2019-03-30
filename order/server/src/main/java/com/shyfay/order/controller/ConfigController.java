package com.shyfay.order.controller;

import com.shyfay.order.config.RedisConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author mx
 * @since 2019/3/27
 */
@RestController
@RequestMapping("/config")
public class ConfigController {

    @Autowired
    private RedisConfig redisConfig;

    @RequestMapping(method= RequestMethod.GET, value="/print")
    public String print(){
        return "hostName: " + redisConfig.getHost() + "password:" + redisConfig.getPassword();
    }
}
