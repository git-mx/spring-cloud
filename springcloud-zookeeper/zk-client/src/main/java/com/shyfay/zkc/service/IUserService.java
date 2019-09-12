package com.shyfay.zkc.service;

import com.shyfay.zkc.domain.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

/**
 * Created by Administrator on 2019/1/26.
 */
@Service
@FeignClient("zk-server")
public interface IUserService {
    @GetMapping("/list")
    List<User> getList();
}
