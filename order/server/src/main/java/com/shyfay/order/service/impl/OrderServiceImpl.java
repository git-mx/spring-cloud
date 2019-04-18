package com.shyfay.order.service.impl;

import com.alibaba.fastjson.JSON;
import com.shyfay.order.dataobject.OrderDetail;
import com.shyfay.order.dataobject.OrderMaster;
import com.shyfay.order.dto.OrderDTO;
import com.shyfay.order.enums.OrderStatusEnum;
import com.shyfay.order.enums.PayStatusEnum;
import com.shyfay.order.exception.OrderException;
import com.shyfay.order.message.MessageSender;
import com.shyfay.order.message.ReceiverInput;
import com.shyfay.order.repository.OrderDetailRepository;
import com.shyfay.order.repository.OrderMasterRepository;
import com.shyfay.order.utils.RedisUtil;
import com.shyfay.order.service.OrderService;
import com.shyfay.order.utils.KeyUtil;
import com.shyfay.product.client.ProductClient;
import com.shyfay.product.common.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by mx
 * 2019-03-23 16:44
 */
@Service
@Slf4j
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @Autowired
    private OrderMasterRepository orderMasterRepository;

    @Autowired
    private ProductClient productClient;

    @Autowired
    private MessageSender messageSender;

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private OrderProcess orderProcess;

    private static final String PRODUCT_REDIS_KEY_PREFIX = "product:%s";
    private static final String PRODUCT_REDIS_LOCK_KEY_PREFIX = "product-%s";

    /**
     * 秒杀场景的话，可以将商品信息放入的redis里面
     * 比如秒杀10个商品，那么就直接在redis里面写入10.就用这10个来秒杀，直接和MYSQL没有关系
     *
     * 我们这里做成如下步骤
     * 1.先将商品信息放入redis
     * 2.创建订单时，从redis里面取出对应的商品信息，判断商品库存是否足够
     * 3.如果足够，则将扣减后的新的库存写入到redis. 2和3必须要加分布式锁
     * 4.扣减掉redis库存后将订单主表和订单详情入库，如果入库失败，要将在2和3扣除的redis的库存又加回去，这个也要加分布式锁
     * 5.订单主表和详情入库之后，需要发送MQ消息给商品服务去扣减MYSQL的库存，商品服务订阅该消息去扣减库存
     * 6.定时任务的补偿机制，可以写一个定时器去检测redis里面的商品库存和MYSQL库的库存是否一致，判断不一致时做出相应的补偿机制
     */
    //只能买家访问
    @Override
    public OrderDTO doOrder(OrderDTO orderDTO){
        String orderId = KeyUtil.genUniqueKey();
        List<OrderDetail> orderDetailList = orderDTO.getOrderDetailList();
        Map<String, Integer> successList = new HashMap<>();
        BigDecimal orderAmont = new BigDecimal(BigInteger.ZERO);
        for(OrderDetail orderDetail : orderDetailList){
            ProductInfoOutput productInfoOutput = reduceRedisStock(orderDetail.getProductId(), orderDetail.getProductQuantity());
            if(null != productInfoOutput){
                BeanUtils.copyProperties(productInfoOutput, orderDetail);
                successList.put(orderDetail.getProductId(), orderDetail.getProductQuantity());
                orderDetail.setDetailId(KeyUtil.genUniqueKey());
                orderDetail.setOrderId(orderId);
                orderAmont = orderDetail.getProductPrice()
                        .multiply(new BigDecimal(orderDetail.getProductQuantity()))
                        .add(orderAmont);
            }else{
                if(successList.size() > 0);
                for (String key : successList.keySet()) {
                    rollbackRedisStock(key, successList.get(key));
                }
                return null;
                //throw new ProductException(ResultEnum.PRODUCT_NOT_EXIST);
            }
        }
        orderDTO.setOrderId(orderId);
        orderDTO.setOrderAmount(orderAmont);
        orderDTO.setOrderStatus(OrderStatusEnum.NEW.getCode());
        orderDTO.setPayStatus(PayStatusEnum.WAIT.getCode());
        orderProcess.updateMysql(orderDTO);
        return orderDTO;
    }

    //异步入库和扣MYSQL库存



    private void rollbackRedisStock(String productId, Integer buyQuantity){
        String lockKey = String.format(PRODUCT_REDIS_LOCK_KEY_PREFIX, productId);
        try {

            int tryLockTime = 0;
            while(tryLockTime < 5){
                boolean islock = redisUtil.lock(lockKey, 300000, 300000);
                if(islock){
                    break;
                }
                //如果尝试4次加锁都失败就不要分布式锁了，直接执行
                tryLockTime++;
            }
            String redisKey = String.format(PRODUCT_REDIS_KEY_PREFIX, productId);
            String jsonStr = redisUtil.get(redisKey);
            ProductInfoOutput productInfoOutput = JSON.parseObject(jsonStr, ProductInfoOutput.class);
            productInfoOutput.setProductStock(productInfoOutput.getProductStock() + buyQuantity);
            jsonStr = JSON.toJSONString(productInfoOutput);
            redisUtil.set(redisKey, jsonStr);
        } catch (Exception e) {
            log.error("回滚redis库存报错", e);
        } finally {
            redisUtil.del(lockKey);
            log.debug("回滚redis库存已经解锁!");
        }
    }

//    private synchronized ProductInfoOutput reduceRedisStock(String productId, Integer buyQuantity) {
//        String redisKey = String.format(PRODUCT_REDIS_KEY_PREFIX, productId);
//        ProductInfoOutput productInfoOutput;
//        String jsonStr = redisUtil.get(redisKey);
//        //redis里面没有对应的商品，那么去调用商品服务获取商品信息
//        if(StringUtils.isBlank(jsonStr)){
//            productInfoOutput = productClient.findByPId(productId);
//        }else{
//            productInfoOutput = JSON.parseObject(jsonStr, ProductInfoOutput.class);
//        }
//        if(null == productInfoOutput){
//            return null;
//        }
//        //库存不够，将本订单扣减的redis库存又加回来并抛出异常
//        if(productInfoOutput.getProductStock() < buyQuantity){
//            return null;
//        }
//        ProductInfoOutput returnResult = productInfoOutput;
//        productInfoOutput.setProductStock(productInfoOutput.getProductStock() - buyQuantity);
//        //扣减redis库存
//        jsonStr = JSON.toJSONString(productInfoOutput);
//        redisUtil.set(redisKey, jsonStr);
//        return returnResult;
//    }

    private ProductInfoOutput reduceRedisStock(String productId, Integer buyQuantity) {
        String lockKey = String.format(PRODUCT_REDIS_LOCK_KEY_PREFIX, productId);
        try {
            boolean islock = redisUtil.lock(lockKey, 300000, 300000);
            if(!islock){
                return null;
            }
            String redisKey = String.format(PRODUCT_REDIS_KEY_PREFIX, productId);
            ProductInfoOutput productInfoOutput;
            String jsonStr = redisUtil.get(redisKey);
            //redis里面没有对应的商品，那么去调用商品服务获取商品信息
            if(StringUtils.isBlank(jsonStr)){
                productInfoOutput = productClient.findByPId(productId);
            }else{
                productInfoOutput = JSON.parseObject(jsonStr, ProductInfoOutput.class);
            }
            if(null == productInfoOutput){
                return null;
            }
            //库存不够，将本订单扣减的redis库存又加回来并抛出异常
            if(productInfoOutput.getProductStock() < buyQuantity){
                return null;
            }
            ProductInfoOutput returnResult = productInfoOutput;
            productInfoOutput.setProductStock(productInfoOutput.getProductStock() - buyQuantity);
            //扣减redis库存
            jsonStr = JSON.toJSONString(productInfoOutput);
            redisUtil.set(redisKey, jsonStr);
            return returnResult;
        } catch (Exception e) {
            log.error("扣减redis库存报错", e);
            return null;
        } finally {
            redisUtil.del(lockKey);
            log.debug("扣减redis库存已经解锁!");
        }
    }


    //只能买家访问
    @Override
    @Transactional
    public OrderDTO create(OrderDTO orderDTO) {
        String orderId = KeyUtil.genUniqueKey();
        List<OrderDetail> orderDetailList = orderDTO.getOrderDetailList();
        List<String> productIdList = new ArrayList<>();
        boolean redisExist = true;
        List<ProductInfoOutput> productInfoOutputList = new ArrayList<>();
        List<ProductInfoStockOutput> productInfoStockOutputList = new ArrayList<>();
        for(OrderDetail orderDetail : orderDetailList){
            productIdList.add(orderDetail.getProductId());
            String jsonStr = redisUtil.get(String.format(PRODUCT_REDIS_KEY_PREFIX, orderDetail.getProductId()));

            if(StringUtils.isBlank(jsonStr)){
                redisExist =false;
            }
            ProductInfoOutput productInfoOutput = JSON.parseObject(jsonStr, ProductInfoOutput.class);
            productInfoOutputList.add(productInfoOutput);
        }
        //查询商品信息(调用商品服务)
        if(!redisExist) {
            productInfoOutputList = productClient.listForOrder(productIdList);
        }
        BigDecimal orderAmont = new BigDecimal(BigInteger.ZERO);
        //可以将商品列表放入redis中
        for(OrderDetail orderDetail : orderDTO.getOrderDetailList()){
            for(ProductInfoOutput productInfo : productInfoOutputList){
                if(orderDetail.getProductId().equals(productInfo.getProductId())){
                    //购买数量大于库存量
                    if(orderDetail.getProductQuantity() > productInfo.getProductStock()){
                        throw new ProductException(ResultEnum.PRODUCT_NOT_EXIST);
                    }
                    BeanUtils.copyProperties(productInfo, orderDetail);
                    orderDetail.setDetailId(KeyUtil.genUniqueKey());
                    orderDetail.setOrderId(orderId);
                    //计算出总价
                    orderAmont = orderDetail.getProductPrice()
                            .multiply(new BigDecimal(orderDetail.getProductQuantity()))
                            .add(orderAmont);
                    //减库存
                    productInfo.setProductStock(productInfo.getProductStock() - orderDetail.getProductQuantity());
                    //将减掉库存的商品信息缓存到redis
                    redisUtil.set(String.format(PRODUCT_REDIS_KEY_PREFIX, productInfo.getProductId()), JSON.toJSONString(productInfo));
                    productInfoStockOutputList.add(new ProductInfoStockOutput(orderDetail.getProductId(), orderDetail.getProductQuantity()));
                }
            }
        }

        //入库orderDetail
        orderDetailRepository.saveAll(orderDTO.getOrderDetailList());
        //扣库存(调用商品服务)
        List<DecreaseStockInput> decreaseStockInputList = orderDTO.getOrderDetailList().stream()
                .map(e -> new DecreaseStockInput(e.getProductId(), e.getProductQuantity()))
                .collect(Collectors.toList());
        //在高并发的情况下，去调用商品服务扣减库存就会导致数据失真的情况
        productClient.decreaseStock(decreaseStockInputList);
        //订单入库
        OrderMaster orderMaster = new OrderMaster();
        orderDTO.setOrderId(orderId);
        BeanUtils.copyProperties(orderDTO, orderMaster);
        orderMaster.setOrderAmount(orderAmont);
        orderMaster.setOrderStatus(OrderStatusEnum.NEW.getCode());
        orderMaster.setPayStatus(PayStatusEnum.WAIT.getCode());
        orderMasterRepository.save(orderMaster);
        //发送消息到商品服务扣减MYSQL库存
        //messageSender.send(productInfoStockOutputList);
        return orderDTO;
    }

    //完结订单, 只能卖家访问
    @Override
    public OrderDTO finish(String orderId) {
        Optional<OrderMaster> optionalOrderMaster = orderMasterRepository.findById(orderId);
        if(!optionalOrderMaster.isPresent()){
            throw new OrderException(com.shyfay.order.enums.ResultEnum.ORDER_NOT_EXIST);
        }
        OrderMaster orderMaster = optionalOrderMaster.get();
        if(OrderStatusEnum.NEW.getCode() != orderMaster.getOrderStatus()){
            throw new OrderException(com.shyfay.order.enums.ResultEnum.ORDER_STATUS_ERROR);
        }
        orderMaster.setOrderStatus(OrderStatusEnum.FINISHED.getCode());
        orderMasterRepository.save(orderMaster);

        OrderDTO orderDTO = new OrderDTO();
        BeanUtils.copyProperties(orderMaster, orderDTO);
        List<OrderDetail> orderDetailList = orderDetailRepository.findByOrderId(orderId);
        if(CollectionUtils.isEmpty(orderDetailList)){
            throw new OrderException(com.shyfay.order.enums.ResultEnum.ORDER_DETAIL_NOT_EXIST);
        }
        orderDTO.setOrderDetailList(orderDetailList);
        return orderDTO;
    }
}
