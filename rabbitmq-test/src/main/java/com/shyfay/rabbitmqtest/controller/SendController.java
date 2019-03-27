package com.shyfay.rabbitmqtest.controller;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author mx
 * @since 2019/3/27
 */
@RequestMapping("/test")
@RestController
public class SendController {
    @Autowired
    private AmqpTemplate amqpTemplate;

    @RequestMapping(method= RequestMethod.GET, value="/send1")
    public void send1(@RequestParam String message){
        amqpTemplate.convertAndSend("testQueue", message);
    }

    //指定发送的key，接收进程使用key来接收不同订单的消息
    @RequestMapping(method= RequestMethod.GET, value="send2")
    public void send2(@RequestParam("type")Integer type, @RequestParam("message")String message){
        if(type.equals(1)){
            amqpTemplate.convertAndSend("myOrder", "computer", message);
        }else{
            amqpTemplate.convertAndSend("myOrder", "fruit", message);
        }
    }
}
