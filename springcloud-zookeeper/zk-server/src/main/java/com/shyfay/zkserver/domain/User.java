package com.shyfay.zkserver.domain;

import lombok.Data;

/**
 * @author mx
 * @since 2019/7/22
 */
@Data
public class User {
    int id;
    String name;
    public User(int id, String name){
        this.id = id;
        this.name = name;
    }
}
