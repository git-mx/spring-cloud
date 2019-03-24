package com.shyfay.orderb.service.impl;

import com.shyfay.orderb.client.ProductClinet;
import com.shyfay.orderb.dataobject.OrderDetail;
import com.shyfay.orderb.dataobject.OrderMaster;
import com.shyfay.orderb.dataobject.ProductInfo;
import com.shyfay.orderb.dto.CartDTO;
import com.shyfay.orderb.dto.OrderDTO;
import com.shyfay.orderb.enums.OrderStatusEnum;
import com.shyfay.orderb.enums.PayStatusEnum;
import com.shyfay.orderb.repository.OrderDetailRepository;
import com.shyfay.orderb.repository.OrderMasterRepository;
import com.shyfay.orderb.service.OrderService;
import com.shyfay.orderb.utils.KeyUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by mx
 * 2019-03-23 16:44
 */
@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @Autowired
    private OrderMasterRepository orderMasterRepository;

    @Autowired
    private ProductClinet productClinet;

    @Override
    @Transactional
    public OrderDTO create(OrderDTO orderDTO) {
        String orderId = KeyUtil.genUniqueKey();
        //查询商品信息(调用商品服务)
        List<String> productIdList = orderDTO.getOrderDetailList().stream()
                .map(OrderDetail::getProductId)
                .collect(Collectors.toList());
        List<ProductInfo> productInfoList = productClinet.listForOrder(productIdList);
        BigDecimal orderAmont = new BigDecimal(BigInteger.ZERO);
        for(OrderDetail orderDetail : orderDTO.getOrderDetailList()){
            for(ProductInfo productInfo : productInfoList){
                if(orderDetail.getProductId().equals(productInfo.getProductId())){
                    BeanUtils.copyProperties(productInfo, orderDetail);
                    orderDetail.setDetailId(KeyUtil.genUniqueKey());
                    orderDetail.setOrderId(orderId);
                    //计算出总价
                    orderAmont = orderDetail.getProductPrice()
                            .multiply(new BigDecimal(orderDetail.getProductQuantity()))
                            .add(orderAmont);
                }
            }
        }
        //入库orderDetail
        orderDetailRepository.saveAll(orderDTO.getOrderDetailList());
        //扣库存(调用商品服务)
        List<CartDTO> cartDTOS = orderDTO.getOrderDetailList().stream()
                .map(e -> new CartDTO(e.getProductId(), e.getProductQuantity()))
                .collect(Collectors.toList());
        productClinet.decreaseStock(cartDTOS);
        //订单入库
        OrderMaster orderMaster = new OrderMaster();
        orderDTO.setOrderId(orderId);
        BeanUtils.copyProperties(orderDTO, orderMaster);
        orderMaster.setOrderAmount(orderAmont);
        orderMaster.setOrderStatus(OrderStatusEnum.NEW.getCode());
        orderMaster.setPayStatus(PayStatusEnum.WAIT.getCode());
        orderMasterRepository.save(orderMaster);
        return orderDTO;
    }
}
