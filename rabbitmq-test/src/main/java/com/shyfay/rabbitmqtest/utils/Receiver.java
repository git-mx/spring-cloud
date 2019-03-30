package com.shyfay.rabbitmqtest.utils;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

/**
 * @author mx
 * @since 2019/3/29
 */
public interface Receiver {
    String INPUT = "input";

    @Input(Receiver.INPUT)
    SubscribableChannel input();

}
