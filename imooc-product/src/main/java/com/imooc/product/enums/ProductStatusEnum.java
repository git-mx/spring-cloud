package com.imooc.product.enums;

import lombok.Getter;

/**
 * @author mx
 * @since 2019/3/23
 */
@Getter
public enum ProductStatusEnum {
    UP(0, "上架"),
    DOWN(1, "下架");

    private Integer code;
    private String msg;
    ProductStatusEnum(Integer code, String msg){
        this.code = code;
        this.msg = msg;
    }
}
