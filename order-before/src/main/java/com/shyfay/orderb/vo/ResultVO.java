package com.shyfay.orderb.vo;

import lombok.Data;

/**
 * Created by mx
 * 2019-03-23 18:02
 */
@Data
public class ResultVO<T> {

    private Integer code;

    private String msg;

    private T data;
}
