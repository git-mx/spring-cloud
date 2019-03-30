package com.shyfay.rabbitmqtest.controller;

import com.shyfay.rabbitmqtest.bean.User;
import com.shyfay.rabbitmqtest.message.StreamSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author mx
 * @since 2019/3/29
 */
@RestController
@RequestMapping("/stream")
public class StreamController {

    @Autowired
    StreamSender streamSender;

    //发送字符串
//    @RequestMapping(method = RequestMethod.GET, value = "/send")
//    public void sendMessage(@RequestParam String message){
//        streamSender.sendMessage(message);
//    }

    @RequestMapping(method = RequestMethod.GET, value = "/send")
    public void sendMessage(){
        User user = new User();
        user.setUserId("1111111");
        user.setUserName("张三");
        streamSender.sendMessage(user);
    }

}
