package com.shyfay.rabbitmqtest.message;

import com.shyfay.rabbitmqtest.bean.User;
import com.shyfay.rabbitmqtest.utils.Receiver;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.stereotype.Component;

/**
 * @author mx
 * @since 2019/3/29
 */
@Component
@EnableBinding(Receiver.class)
@Slf4j
public class StreamReceiver {
    //接收字符串类型的消息
//    @StreamListener(value = Receiver.INPUT)
//    public void process(String message) {
//        log.info("StreamReceiver: {}", message);
//    }

    //接收对象，将如下代码注释之后，就么有消息消费者，这样消息就会一直留在rabbitMQ的队列里
//    @StreamListener(value = Receiver.INPUT)
//    public void process(User user) {
//        log.info("StreamReceiver: {}", user);
//    }

}
