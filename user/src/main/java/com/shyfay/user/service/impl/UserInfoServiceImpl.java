package com.shyfay.user.service.impl;

import com.shyfay.user.dataobject.UserInfo;
import com.shyfay.user.repository.UserInfoRepository;
import com.shyfay.user.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author mx
 * @since 2019/4/17
 */
@Service
public class UserInfoServiceImpl implements UserInfoService {

    @Autowired
    UserInfoRepository userInfoRepository;

    @Override
    public UserInfo findByOpenid(String openid) {
        return userInfoRepository.findByOpenid(openid);
    }
}
