package com.shyfay.product.client;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.shyfay.product.common.DecreaseStockInput;
import com.shyfay.product.common.ProductInfoOutput;

import java.util.List;

/**
 * @author mx
 * @since 2019/3/24
 */
@FeignClient("PRODUCT")
@Component
public interface ProductClient {
    @RequestMapping(method= RequestMethod.GET, value="/server/msg")
    String getMessageFromProduct();

    @RequestMapping(method = RequestMethod.POST, value="/product/listForOrder")
    List<ProductInfoOutput> listForOrder(List<String> productIdList);

    @RequestMapping(method=RequestMethod.POST, value="/product/decreaseStock")
    void decreaseStock(List<DecreaseStockInput> decreaseStockInputList);

}
