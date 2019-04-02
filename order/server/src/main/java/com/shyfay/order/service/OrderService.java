package com.shyfay.order.service;

import com.shyfay.order.dto.OrderDTO;

/**
 * Created by mx
 * 2019-03-23 16:39
 */
public interface OrderService {

    /**
     * 创建订单
     * @param orderDTO
     * @return
     */
    OrderDTO create(OrderDTO orderDTO);

    OrderDTO doOrder(OrderDTO orderDTO);

}
