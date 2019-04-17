package com.shyfay.user.dataobject;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * @author mx
 * @since 2019/4/17
 */
@Data
@Entity
public class UserInfo {

    @Id
    private String id;

    private String username;

    private String password;

    private String openid;

    private Integer role;
}
