package com.shyfay.product.service.impl;

import com.shyfay.product.dataobject.ProductInfo;
import com.shyfay.product.dto.CartDTO;
import com.shyfay.product.enums.ProductStatusEnum;
import com.shyfay.product.enums.ResultEnum;
import com.shyfay.product.exception.ProductException;
import com.shyfay.product.repository.ProductInfoRepository;
import com.shyfay.product.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
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
    @Transactional
    public void decreaseStock(List<CartDTO> decreaseStockInputList) {
        for(CartDTO cartDTO : decreaseStockInputList){
            Optional<ProductInfo> productInfoOptional = productInfoRepository.findById(cartDTO.getProductId());
            if(!productInfoOptional.isPresent()){
                throw new ProductException(ResultEnum.PRODUCT_NOT_EXIST);
            }
            ProductInfo productInfo = productInfoOptional.get();
            Integer stock = productInfo.getProductStock() - cartDTO.getProductQuantity();
            if(stock < 0){
                throw new ProductException(ResultEnum.PRODUCT_STOCK_ERROR);
            }
            productInfo.setProductStock(stock);
            productInfoRepository.save(productInfo);
        }
    }
}
