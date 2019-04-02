package com.shyfay.order.service.impl;

import com.shyfay.order.dataobject.OrderMaster;
import com.shyfay.order.dto.OrderDTO;
import com.shyfay.order.enums.OrderStatusEnum;
import com.shyfay.order.enums.PayStatusEnum;
import com.shyfay.order.message.MessageSender;
import com.shyfay.order.repository.OrderDetailRepository;
import com.shyfay.order.repository.OrderMasterRepository;
import com.shyfay.product.common.ProductInfoStockOutput;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author mx
 * @since 2019/4/2
 */
@Component
@Slf4j
public class OrderProcess {

    @Autowired
    OrderDetailRepository orderDetailRepository;
    @Autowired
    OrderMasterRepository orderMasterRepository;
    @Autowired
    MessageSender messageSender;

    @Async
    public void updateMysql(OrderDTO orderDTO){
        try{
            orderDetailRepository.saveAll(orderDTO.getOrderDetailList());
            //订单入库
            OrderMaster orderMaster = new OrderMaster();
            BeanUtils.copyProperties(orderDTO, orderMaster);
            orderMaster.setOrderAmount(orderDTO.getOrderAmount());
            orderMaster.setOrderStatus(OrderStatusEnum.NEW.getCode());
            orderMaster.setPayStatus(PayStatusEnum.WAIT.getCode());
            orderMasterRepository.save(orderMaster);
            //发送消息到商品服务扣减MYSQL库存
            List<ProductInfoStockOutput> productInfoStockOutputList = orderDTO.getOrderDetailList().stream()
                    .map(e -> {
                        ProductInfoStockOutput productInfoStockOutput = new ProductInfoStockOutput(e.getProductId(), e.getProductQuantity());
                        return productInfoStockOutput;
                    }).collect(Collectors.toList());
            messageSender.send(productInfoStockOutputList);
        }catch (Exception e){
            log.info("入库报错：{}", orderDTO.getOrderId());
        }
    }
}
