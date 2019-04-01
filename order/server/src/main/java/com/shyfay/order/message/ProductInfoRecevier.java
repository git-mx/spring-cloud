package com.shyfay.order.message;

import com.shyfay.product.common.ProductInfoStockOutput;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author mx
 * @since 2019/3/30
 */
//@Slf4j
//@Component
//@EnableBinding(ReceiverInput.class)
public class ProductInfoRecevier {

//    private final String PRODUCT_INFO_REDIS_KEY_PREFIX = "product-id-%s";
//
//    @Autowired
//    StringRedisTemplate stringRedisTemplate;
//
//    @StreamListener(value = ReceiverInput.INPUT)
//    public void productStockProcess(List<ProductInfoStockOutput> productInfoStockOutputList){
//        String redisKey = "";
//        for(ProductInfoStockOutput output : productInfoStockOutputList){
//            redisKey = String.format(PRODUCT_INFO_REDIS_KEY_PREFIX, output.getProductId());
//            stringRedisTemplate.opsForValue().set(redisKey, String.valueOf(output.getProductStock()));
//        }
//    }

}
