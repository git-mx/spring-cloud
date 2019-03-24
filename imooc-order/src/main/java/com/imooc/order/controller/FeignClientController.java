package com.imooc.order.controller;

import com.imooc.order.client.ProductClinet;
import com.imooc.order.dataobject.ProductInfo;
import com.imooc.order.dto.CartDTO;
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
    ProductClinet feignClient;

    @RequestMapping(method= RequestMethod.GET, value="/get")
    public String getMessage(){
        return feignClient.getMessageFromProduct();
    }

    @RequestMapping(method = RequestMethod.GET, value="/listForOrder")
    public String listForOrder(){
        List<ProductInfo> response = feignClient.listForOrder(Arrays.asList("157875196366160022","157875227953464068"));
        log.info("response={}", response);
        return "OK";
    }

    @RequestMapping(method=RequestMethod.GET, value="/decreaseStock")
    public String decreaseStock(){
        CartDTO cartDTO = new CartDTO("164103465734242707", 2);
        feignClient.decreaseStock(Arrays.asList(cartDTO));
        return "OK";
    }
}
