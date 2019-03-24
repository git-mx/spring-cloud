package com.imooc.product.repository;

import com.google.common.collect.Lists;
import com.imooc.product.ImoocProductApplicationTests;
import com.imooc.product.dataobject.ProductInfo;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.List;

/**
 * @author mx
 * @since 2019/3/23
 */
public class ProductInfoRepositoryTest extends ImoocProductApplicationTests {

    @Autowired
    ProductInfoRepository productInfoRepository;

    @Test
    public void findByProductStatus() {
        List<ProductInfo> result = productInfoRepository.findByProductStatus(0);
        Assert.assertTrue(result.size() > 0);
    }

    @Test
    public void findByProductIdIn(){
        List<ProductInfo> result = productInfoRepository.findByProductIdIn(Arrays.asList("157875196366160022", "157875227953464068"));
        Assert.assertTrue(result.size() > 0);
    }
}