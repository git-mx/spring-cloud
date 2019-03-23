package com.imooc.product.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author mx
 * @since 2019/3/23
 */
@RestController
@RequestMapping("/server")
public class ServerController {

    @GetMapping("/msg")
    public String msg(){
        return "this is message from prodcut....";
    }
}
