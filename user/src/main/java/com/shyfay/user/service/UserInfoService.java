package com.shyfay.user.service;

import com.shyfay.user.dataobject.UserInfo;

/**
 * @author mx
 * @since 2019/4/17
 */
public interface UserInfoService {
    UserInfo findByOpenid(String openid);
}
