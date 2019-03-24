package com.shyfay.productb.service;

import com.shyfay.productb.dataobject.ProductCategory;

import java.util.List;

/**
 * @author mx
 * @since 2019/3/23
 */
public interface CategoryService {
    List<ProductCategory> findByCategoryTypeIn(List<Integer> categoryTypeList);
}
