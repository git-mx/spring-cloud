package com.shyfay.productb.service.impl;

import com.shyfay.productb.dataobject.ProductCategory;
import com.shyfay.productb.repository.ProductCategoryRepository;
import com.shyfay.productb.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author mx
 * @since 2019/3/23
 */
@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private ProductCategoryRepository productCategoryRepository;

    @Override
    public List<ProductCategory> findByCategoryTypeIn(List<Integer> categoryTypeList) {
        return productCategoryRepository.findByCategoryTypeIn(categoryTypeList);
    }
}
