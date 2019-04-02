package com.shyfay.product.service.impl;

import com.shyfay.product.common.*;
import com.shyfay.product.dataobject.ProductInfo;
import com.shyfay.product.enums.ProductStatusEnum;
//import com.shyfay.product.message.MessageSender;
import com.shyfay.product.repository.ProductInfoRepository;
import com.shyfay.product.service.ProductService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author mx
 * @since 2019/3/23
 */
@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductInfoRepository productInfoRepository;

//    @Autowired
//    MessageSender messageSender;

    @Override
    public List<ProductInfo> findUpAll() {
        return productInfoRepository.findByProductStatus(ProductStatusEnum.UP.getCode());
    }

    @Override
    public List<ProductInfo> findList(List<String> productIdList){
        List<ProductInfo> productInfoList = productInfoRepository.findByProductIdIn(productIdList);
        return productInfoList;
    }

    @Override
    public ProductInfoOutput findByProductId(String productId) {
        Optional<ProductInfo> productInfoOptional = productInfoRepository.findById(productId);
        if(!productInfoOptional.isPresent()){
            throw new ProductException(ResultEnum.PRODUCT_NOT_EXIST);
        }
        ProductInfo productInfo = productInfoOptional.get();
        ProductInfoOutput productInfoOutput = new ProductInfoOutput();
        BeanUtils.copyProperties(productInfo, productInfoOutput);
        return productInfoOutput;
    }

    @Override
    public void decreaseStock(List<DecreaseStockInput> decreaseStockInputList) {
        List<ProductInfoStockOutput> productInfoStockOutputList = decreaseStockProcess(decreaseStockInputList);
        if(!CollectionUtils.isEmpty(productInfoStockOutputList)){
            //messageSender.send(productInfoStockOutputList);
        }
    }

    @Transactional
    public List<ProductInfoStockOutput> decreaseStockProcess(List<DecreaseStockInput> decreaseStockInputList){
        List<ProductInfoStockOutput> productInfoStockOutputList = new ArrayList<>();
        for(DecreaseStockInput input : decreaseStockInputList){
            Optional<ProductInfo> productInfoOptional = productInfoRepository.findById(input.getProductId());
            if(!productInfoOptional.isPresent()){
                throw new ProductException(ResultEnum.PRODUCT_NOT_EXIST);
            }
            ProductInfo productInfo = productInfoOptional.get();
            Integer stock = productInfo.getProductStock() - input.getProductQuantity();
            if(stock < 0){
                throw new ProductException(ResultEnum.PRODUCT_STOCK_ERROR);
            }
            productInfo.setProductStock(stock);
            productInfoRepository.save(productInfo);
            productInfoStockOutputList.add(new ProductInfoStockOutput(productInfo.getProductId(), productInfo.getProductStock()));
        }
        return productInfoStockOutputList;
    }
}
