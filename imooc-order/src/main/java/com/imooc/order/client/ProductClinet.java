package com.imooc.order.client;

import com.imooc.order.dataobject.ProductInfo;
import com.imooc.order.dto.CartDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

/**
 * @author mx
 * @since 2019/3/24
 */
@FeignClient("IMOOC-PRODUCT")
@Component
public interface ProductClinet {
    @RequestMapping(method= RequestMethod.GET, value="/server/msg")
    String getMessageFromProduct();

    @RequestMapping(method = RequestMethod.POST, value="/product/listForOrder")
    List<ProductInfo> listForOrder(List<String> productIdList);

    @RequestMapping(method=RequestMethod.POST, value="/product/decreaseStock")
    void decreaseStock(List<CartDTO> decreaseStockInputList);

}
