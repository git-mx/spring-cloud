package com.shyfay.apigateway.exception;

/**
 * @author mx
 * @since 2019/4/16
 */
public class BusinessException extends RuntimeException {
    private int code;
    private String msg;

}
