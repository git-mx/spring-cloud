package com.shyfay.order.controller;

import com.shyfay.order.dataobject.ProductInfo;
import com.shyfay.order.dto.CartDTO;
import com.shyfay.product.client.ProductClient;
import com.shyfay.product.common.DecreaseStockInput;
import com.shyfay.product.common.ProductInfoOutput;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

/**
 * @author mx
 * @since 2019/3/24
 */
@RestController
@RequestMapping("/feign")
@Slf4j
public class FeignClientController {

    @Autowired
    ProductClient feignClient;

    @RequestMapping(method= RequestMethod.GET, value="/get")
    public String getMessage(){
        return feignClient.getMessageFromProduct();
    }

    @RequestMapping(method = RequestMethod.GET, value="/listForOrder")
    public String listForOrder(){
        List<ProductInfoOutput> response = feignClient.listForOrder(Arrays.asList("157875196366160022","157875227953464068"));
        log.info("response={}", response);
        return "OK";
    }

    @RequestMapping(method= RequestMethod.GET, value="/decreaseStock")
    public String decreaseStock(){
        DecreaseStockInput decreaseStockInput = new DecreaseStockInput("164103465734242707", 2);
        feignClient.decreaseStock(Arrays.asList(decreaseStockInput));
        return "OK";
    }
}
