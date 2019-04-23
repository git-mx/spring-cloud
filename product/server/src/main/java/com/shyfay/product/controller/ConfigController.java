package com.shyfay.product.controller;

import com.shyfay.product.config.GirlConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author mx
 * @since 2019/4/23
 */
@RestController
public class ConfigController {
    @Autowired
    GirlConfig girlConfig;

    @RequestMapping(value = "/config/test", method = RequestMethod.GET)
    public String test()  {
        //设置超时
        return "name:" + girlConfig.getName() + ", age:" + girlConfig.getAge();
    }
}
