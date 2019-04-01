package com.shyfay.product.message;

import com.shyfay.product.common.ProductException;
import com.shyfay.product.common.ProductInfoStockOutput;
import com.shyfay.product.common.ResultEnum;
import com.shyfay.product.dataobject.ProductInfo;
import com.shyfay.product.repository.ProductInfoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author mx
 * @since 2019/3/30
 */
@Slf4j
@Component
@EnableBinding(ReceiverInput.class)
public class ProductInfoRecevier {

    @Autowired
    ProductInfoRepository productInfoRepository;

    //扣减MySQL库存
    @StreamListener(value = ReceiverInput.INPUT)
    public void productStockProcess(List<ProductInfoStockOutput> productInfoStockOutputList){
        List<ProductInfo> productInfoList = new ArrayList<>();
        for(ProductInfoStockOutput  productInfoStockOutput : productInfoStockOutputList){
            Optional<ProductInfo> productInfoOptional = productInfoRepository.findById(productInfoStockOutput.getProductId());
            if(!productInfoOptional.isPresent()){
                throw new ProductException(ResultEnum.PRODUCT_NOT_EXIST);
            }
            ProductInfo productInfo = productInfoOptional.get();
            productInfo.setProductStock(productInfo.getProductStock() - productInfoStockOutput.getProductStock());
            productInfoList.add(productInfo);
        }

        productInfoRepository.saveAll(productInfoList);
    }

}
