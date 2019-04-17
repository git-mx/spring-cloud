package com.shyfay.user.repository;

import com.shyfay.user.dataobject.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author mx
 * @since 2019/4/17
 */
public interface UserInfoRepository extends JpaRepository<UserInfo, String> {
    UserInfo findByOpenid(String openid);
}
