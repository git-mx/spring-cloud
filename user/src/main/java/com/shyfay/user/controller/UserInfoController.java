package com.shyfay.user.controller;

import com.shyfay.user.constant.CookieConstant;
import com.shyfay.user.constant.RedisConstant;
import com.shyfay.user.dataobject.UserInfo;
import com.shyfay.user.enums.ResultEnum;
import com.shyfay.user.enums.RoleEnum;
import com.shyfay.user.service.UserInfoService;
import com.shyfay.user.utils.CookieUtil;
import com.shyfay.user.utils.ResultVOUtil;
import com.shyfay.user.vo.ResultVO;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @author mx
 * @since 2019/4/17
 */
@RequestMapping("/login")
@RestController
public class UserInfoController {

    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @RequestMapping("/buyer")
    public ResultVO buyer(@RequestParam("openid")String openid, HttpServletResponse response){
        UserInfo userInfo = userInfoService.findByOpenid(openid);
        if(null == userInfo){
            return ResultVOUtil.error(ResultEnum.LOGIN_FAIL);
        }

        if(RoleEnum.BUYER.getCode() != userInfo.getRole()){
            return ResultVOUtil.error(ResultEnum.ROLE_ERROR);
        }

        CookieUtil.set(response, CookieConstant.OPENID, openid, CookieConstant.expire);

        return ResultVOUtil.success();
    }

    @RequestMapping("/seller")
    public ResultVO seller(@RequestParam("openid")String openid, HttpServletRequest request, HttpServletResponse response){
        Cookie cookie = CookieUtil.get(request, CookieConstant.TOKEN);
        if(cookie != null &&
                StringUtils.isNotBlank(stringRedisTemplate.opsForValue().get(String.format(RedisConstant.TOKEN_TEMPLATE, cookie.getValue())))){
            return ResultVOUtil.success();
        }
        UserInfo userInfo = userInfoService.findByOpenid(openid);
        if(null == userInfo){
            return ResultVOUtil.error(ResultEnum.LOGIN_FAIL);
        }

        if(userInfo.getRole() != RoleEnum.SELLER.getCode()){
            return ResultVOUtil.error(ResultEnum.ROLE_ERROR);
        }
        String token = UUID.randomUUID().toString();
        Integer expire = CookieConstant.expire;
        stringRedisTemplate.opsForValue().set(String.format(RedisConstant.TOKEN_TEMPLATE, token), openid, expire, TimeUnit.SECONDS);
        CookieUtil.set(response, CookieConstant.TOKEN, token, CookieConstant.expire);
        return ResultVOUtil.success();
    }
}
