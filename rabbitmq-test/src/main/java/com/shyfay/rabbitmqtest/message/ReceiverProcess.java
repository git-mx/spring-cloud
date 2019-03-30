package com.shyfay.rabbitmqtest.message;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @author mx
 * @since 2019/3/27
 */
@Component
@Slf4j
public class ReceiverProcess {
    //1.在rabbitMQ的控制中心创建好testQueue
//    @RabbitListener(queues="testQueue")
//    public void receiveProcess(String message){
//        log.info(message);
//    }

    //2.在rabbitMQ的控制中心删除掉testQueue，直接在receiveProcess端定义队列
//    @RabbitListener(queuesToDeclare = @Queue("testQueue"))
//    public void receiveProcess(String message){
//        log.info(message);
//    }


    //3.自动创建消息队列，并和Exchange绑定
//    @RabbitListener(bindings = @QueueBinding(
//            value = @Queue("testQueue"),
//            exchange = @Exchange("testExchange")
//    ))
//    public void recevieProcess(String message){
//        log.info(message);
//    }


    //4.通过key来限定只让订阅了自己消息的接受者接受对应的消息，没有订阅该消息的接受者接收不到该消息
    //  比如商城系统中的水果商城服务和电脑商城服务，那么对于订单服务是公用的，水果商城只接收那一部分
    //   水果订单的消息，电脑商城只接收电脑订单的消息
    @RabbitListener(bindings = @QueueBinding(
            exchange = @Exchange("myOrder"),
            key = "computer",
            value = @Queue("computerQueue")

    ))
    public void processComputer(String message){
        log.info("computer: " + message);
    }

    @RabbitListener(bindings = @QueueBinding(
            exchange = @Exchange("myOrder"),
            key = "fruit",
            value = @Queue("fruitQueue")
    ))
    public void processFruit(String message){
        log.info("fruit: " + message);
    }

}
