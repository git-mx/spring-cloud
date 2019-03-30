package com.shyfay.product.common;

import lombok.Data;

/**
 * @author mx
 * @since 2019/3/30
 */
@Data
public class ProductInfoStockOutput {
    private String productId;
    private Integer productStock;

    public ProductInfoStockOutput(String productId, Integer productStock){
        this.productId = productId;
        this.productStock = productStock;
    }
}
