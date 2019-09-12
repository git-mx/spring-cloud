package com.shyfay.zkc.controller;

import com.shyfay.zkc.domain.User;
import com.shyfay.zkc.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by Administrator on 2019/1/26.
 */
@RestController
public class UserController {

    @Autowired
    protected IUserService userService;

    @GetMapping("/list")
    public List<User> getList() {
        return userService.getList();
    }
}
