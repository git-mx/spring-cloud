package com.shyfay.order.exception;

import com.shyfay.order.enums.ResultEnum;

/**
 * Created by mx
 * 2019-03-23 17:27
 */
public class OrderException extends RuntimeException {

    private Integer code;

    public OrderException(Integer code, String message) {
        super(message);
        this.code = code;
    }

    public OrderException(ResultEnum resultEnum) {
        super(resultEnum.getMessage());
        this.code = resultEnum.getCode();
    }
}
