package com.shyfay.productb.service;

import com.shyfay.productb.dataobject.ProductInfo;
import com.shyfay.productb.dto.CartDTO;

import java.util.List;

/**
 * @author mx
 * @since 2019/3/23
 */
public interface ProductService {
    /**
     * 查询所有在架商品列表
     */
    List<ProductInfo> findUpAll();

    /**
     * 查询商品列表
     * @param productIdList
     * @return
     */
    List<ProductInfo> findList(List<String> productIdList);

    /**
     * 扣库存
     * @param decreaseStockInputList
     */
    void decreaseStock(List<CartDTO> decreaseStockInputList);
}
