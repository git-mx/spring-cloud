package com.shyfay.productb.controller;

import com.shyfay.productb.dataobject.ProductCategory;
import com.shyfay.productb.dataobject.ProductInfo;
import com.shyfay.productb.dto.CartDTO;
import com.shyfay.productb.service.CategoryService;
import com.shyfay.productb.service.ProductService;
import com.shyfay.productb.utils.ResultVOUtil;
import com.shyfay.productb.vo.ProductInfoVO;
import com.shyfay.productb.vo.ProductVO;
import com.shyfay.productb.vo.ResultVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author mx
 * @since 2019/3/23
 */
@RestController
@RequestMapping("/product")
public class ProductController {
    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/list")
    public ResultVO<ProductInfo> list(){
        List<ProductInfo> productInfoList = productService.findUpAll();
        List<Integer> categoryTypeList = productInfoList.stream()
                .map(ProductInfo::getCategoryType)
                .collect(Collectors.toList());
        List<ProductCategory> categoryList = categoryService.findByCategoryTypeIn(categoryTypeList);
        List<ProductVO> productVOList = new ArrayList<>();
        for (ProductCategory productCategory: categoryList) {
            ProductVO productVO = new ProductVO();
            productVO.setCategoryName(productCategory.getCategoryName());
            productVO.setCategoryType(productCategory.getCategoryType());

            List<ProductInfoVO> productInfoVOList = new ArrayList<>();
            for (ProductInfo productInfo: productInfoList) {
                if (productInfo.getCategoryType().equals(productCategory.getCategoryType())) {
                    ProductInfoVO productInfoVO = new ProductInfoVO();
                    BeanUtils.copyProperties(productInfo, productInfoVO);
                    productInfoVOList.add(productInfoVO);
                }
            }
            productVO.setProductInfoVOList(productInfoVOList);
            productVOList.add(productVO);
        }
        return ResultVOUtil.success(productVOList);
    }

    @RequestMapping(method= RequestMethod.POST, value="/listForOrder")
    public List<ProductInfo> listForOrder(@RequestBody List<String> productIdList){
        return productService.findList(productIdList);
    }

    @RequestMapping(method= RequestMethod.POST, value="/decreaseStock")
    public void decreaseStock(@RequestBody List<CartDTO> decreaseStockInputList) {
        productService.decreaseStock(decreaseStockInputList);
    }
}
