package com.shyfay.product.service;

import com.shyfay.product.dataobject.ProductCategory;

import java.util.List;

/**
 * @author mx
 * @since 2019/3/23
 */
public interface CategoryService {
    List<ProductCategory> findByCategoryTypeIn(List<Integer> categoryTypeList);
}
