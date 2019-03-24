package com.shyfay.order.dto;

import lombok.Data;

/**
 * @author mx
 * @since 2019/3/24
 */
@Data
public class CartDTO {
    private String productId;

    private Integer productQuantity;

    public CartDTO(){}

    public CartDTO(String productId, Integer productQuantity) {
        this.productId = productId;
        this.productQuantity = productQuantity;
    }
}
