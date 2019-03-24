package com.shyfay.orderb.service;

import com.shyfay.orderb.dto.OrderDTO;

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

}
