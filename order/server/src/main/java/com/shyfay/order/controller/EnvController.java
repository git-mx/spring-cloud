package com.shyfay.order.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author mx
 * @since 2019/3/26
 */
@RestController
@RequestMapping("/env")
public class EnvController {

    @Value("${env}")
    private String env;

    @RequestMapping(method= RequestMethod.GET, value="/print")
    public String print(){
        System.out.println(env);
        return env;
    }
}
