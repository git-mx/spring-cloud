package com.shyfay.user.vo;

import lombok.Data;

/**
 * @author mx
 * @since 2019/4/17
 */
@Data
public class ResultVO<T> {
    private Integer code;
    private String message;
    private T data;

}
