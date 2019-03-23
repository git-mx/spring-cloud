package com.imooc.product.vo;

import lombok.Data;

/**
 * response result
 *
 * @author 牟雪
 * @since 2019/3/23
 */
@Data
public class ResultVO<T> {
    private Integer code;
    private String msg;
    T data;
}
