package com.shyfay.order.controller;

import com.shyfay.order.config.GirlConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author mx
 * @since 2019/3/27
 */
@RestController
@RequestMapping("/girl")
public class GirlController {

    @Autowired
    private GirlConfig girlConfig;

    @RequestMapping(method= RequestMethod.GET, value="/print")
    public String print(){
        return "name: " + girlConfig.getName() + " age:" + girlConfig.getAge();
    }
}
