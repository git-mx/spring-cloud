package com.shyfay.orderb.client;

import com.shyfay.orderb.dataobject.ProductInfo;
import com.shyfay.orderb.dto.CartDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

/**
 * @author mx
 * @since 2019/3/24
 */
@FeignClient("PRODUCTB")
@Component
public interface ProductClinet {
    @RequestMapping(method= RequestMethod.GET, value="/server/msg")
    String getMessageFromProduct();

    @RequestMapping(method = RequestMethod.POST, value="/product/listForOrder")
    List<ProductInfo> listForOrder(List<String> productIdList);

    @RequestMapping(method= RequestMethod.POST, value="/product/decreaseStock")
    void decreaseStock(List<CartDTO> decreaseStockInputList);

}
