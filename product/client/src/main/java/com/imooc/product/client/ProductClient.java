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
//@FeignClient(value = "PRODUCT")
//配置feign-hystrix，feign它是自带hystrix，所以不需要额外的pom依赖
@FeignClient(value = "PRODUCT", fallback = ProductClient.ProductClientFallBack.class)
@Component
public interface ProductClient {
    @RequestMapping(method= RequestMethod.GET, value="/server/msg")
    String getMessageFromProduct();

    @RequestMapping(method = RequestMethod.POST, value="/product/listForOrder")
    List<ProductInfoOutput> listForOrder(List<String> productIdList);

    @RequestMapping(method = RequestMethod.POST, value="/product/findByPId")
    ProductInfoOutput findByPId(String productId);

    @RequestMapping(method=RequestMethod.POST, value="/product/decreaseStock")
    void decreaseStock(List<DecreaseStockInput> decreaseStockInputList);

    @Component
    class ProductClientFallBack implements ProductClient {
        @Override
        public String getMessageFromProduct() {
            return null;
        }

        @Override
        public List<ProductInfoOutput> listForOrder(List<String> productIdList) {
            return null;
        }

        @Override
        public ProductInfoOutput findByPId(String productId) {
            return null;
        }

        @Override
        public void decreaseStock(List<DecreaseStockInput> decreaseStockInputList) {

        }
    }

}
