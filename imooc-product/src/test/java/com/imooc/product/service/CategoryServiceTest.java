package com.imooc.product.service;

import com.imooc.product.ImoocProductApplicationTests;
import com.imooc.product.dataobject.ProductCategory;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;


/**
 * @author mx
 * @since 2019/3/23
 */
@Component
public class CategoryServiceTest extends ImoocProductApplicationTests {

    @Autowired
    private CategoryService categoryService;

    @Test
    public void findByCategoryTypeIn() {
        List<ProductCategory> result = categoryService.findByCategoryTypeIn(Arrays.asList(11,12));
        Assert.assertTrue(result.size() > 0);
    }
}