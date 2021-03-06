package com.shyfay.order.controller;

import com.shyfay.order.converter.OrderForm2OrderDTOConverter;
import com.shyfay.order.dto.OrderDTO;
import com.shyfay.order.enums.ResultEnum;
import com.shyfay.order.exception.OrderException;
import com.shyfay.order.form.OrderForm;
import com.shyfay.order.service.OrderService;
import com.shyfay.order.utils.ResultVOUtil;
import com.shyfay.order.vo.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by mx
 * 2019-03-23 16:36
 */
@RestController
@RequestMapping("/order")
@Slf4j
public class OrderController {

    @Autowired
    private OrderService orderService;

    /**
     * 1. 参数检验
     * 2. 查询商品信息(调用商品服务)
     * 3. 计算总价
     * 4. 扣库存(调用商品服务)
     * 5. 订单入库
     */
    @PostMapping("/create")
    public ResultVO<Map<String, String>> create(@Valid OrderForm orderForm,
                           BindingResult bindingResult) {
        if (bindingResult.hasErrors()){
            log.error("【创建订单】参数不正确, orderForm={}", orderForm);
            throw new OrderException(ResultEnum.PARAM_ERROR.getCode(),
                    bindingResult.getFieldError().getDefaultMessage());
        }
        // orderForm -> orderDTO
        OrderDTO orderDTO = OrderForm2OrderDTOConverter.convert(orderForm);
        if (CollectionUtils.isEmpty(orderDTO.getOrderDetailList())) {
            log.error("【创建订单】购物车信息为空");
            throw new OrderException(ResultEnum.CART_EMPTY);
        }

        //OrderDTO result = orderService.create(orderDTO);
        OrderDTO result = orderService.doOrder(orderDTO);

        Map<String, String> map = new HashMap<>();
        map.put("orderId", result.getOrderId());
        //map.put("orderId", "1111");
        return ResultVOUtil.success(map);
    }

    @PostMapping("/create1")
    public ResultVO<Map<String, String>> create1(@Valid OrderForm orderForm,
                                                BindingResult bindingResult) {
        if (bindingResult.hasErrors()){
            log.error("【创建订单】参数不正确, orderForm={}", orderForm);
            throw new OrderException(ResultEnum.PARAM_ERROR.getCode(),
                    bindingResult.getFieldError().getDefaultMessage());
        }
        // orderForm -> orderDTO
        OrderDTO orderDTO = OrderForm2OrderDTOConverter.convert(orderForm);
        if (CollectionUtils.isEmpty(orderDTO.getOrderDetailList())) {
            log.error("【创建订单】购物车信息为空");
            throw new OrderException(ResultEnum.CART_EMPTY);
        }

        //OrderDTO result = orderService.create(orderDTO);
        OrderDTO result = orderService.create(orderDTO);

        Map<String, String> map = new HashMap<>();
        map.put("orderId", result.getOrderId());
        return ResultVOUtil.success(map);
    }

    @RequestMapping(value="/finish", method = RequestMethod.GET)
    public ResultVO<OrderDTO> finish(@RequestParam("orderId")String orderId){
        return ResultVOUtil.success(orderService.finish(orderId));
    }



}
