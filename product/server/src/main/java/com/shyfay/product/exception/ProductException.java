package com.shyfay.product.exception;

import com.shyfay.product.enums.ResultEnum;

/**
 * Created by mx
 * 2017-12-10 22:59
 */
public class ProductException extends RuntimeException {

    private Integer code;

    public ProductException(Integer code, String message) {
        super(message);
        this.code = code;
    }

    public ProductException(ResultEnum resultEnum) {
        super(resultEnum.getMessage());
        this.code = resultEnum.getCode();
    }
}
