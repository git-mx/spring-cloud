package com.imooc.product.service;

import com.imooc.product.ImoocProductApplicationTests;
import com.imooc.product.dataobject.ProductInfo;
import com.imooc.product.dto.CartDTO;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
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

    @Test
    public void findList(){
        List<ProductInfo> result = productService.findList(Arrays.asList("157875196366160022","157875227953464068"));
        Assert.assertTrue(result.size() > 0);
    }

    @Test
    public void decreaseStock(){
        CartDTO cartDTO = new CartDTO("164103465734242707", 2);
        productService.decreaseStock(Arrays.asList(cartDTO));
    }
}