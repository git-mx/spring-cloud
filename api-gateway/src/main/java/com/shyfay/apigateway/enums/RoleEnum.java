package com.shyfay.apigateway.enums;

import lombok.Getter;

/**
 * @author mx
 * @since 2019/4/17
 */
@Getter
public enum RoleEnum {

    BUYER(1, "买家")
    ,SELLER(2, "卖家")
    ;

    private Integer code;
    private String name;

    RoleEnum(Integer code, String name){
        this.code = code;
        this.name = name;
    }
}
