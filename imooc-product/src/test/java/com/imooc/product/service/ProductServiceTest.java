package com.imooc.product.service;

import com.imooc.product.ImoocProductApplicationTests;
import com.imooc.product.dataobject.ProductInfo;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;


/**
 * @author mx
 * @since 2019/3/23
 */
public class ProductServiceTest extends ImoocProductApplicationTests {


    @Autowired
    private ProductService productService;

    @Test
    public void findUpAll() {
        List<ProductInfo> result = productService.findUpAll();
        Assert.assertTrue(result.size() > 0);
    }
}