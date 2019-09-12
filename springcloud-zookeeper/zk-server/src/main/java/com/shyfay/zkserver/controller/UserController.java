package com.shyfay.zkserver.controller;

import com.shyfay.zkserver.domain.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * @author mx
 * @since 2019/7/22
 */
@RestController
public class UserController {
    @Value("${server.port}")
    private int port;

    @GetMapping("/list")
    public List<User> getList(){
        return new ArrayList<User>(){
            {
                add(new User(1, "user_001" + port));
                add(new User(2, "user_002" + port));
                add(new User(3, "user_003" + port));
            }
        };
    }
}
