package com.shyfay.rabbitmqtest.utils;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

/**
 * @author mx
 * @since 2019/3/29
 */
public interface Sender {
    String OUTPUT = "output";

    @Output(Sender.OUTPUT)
    MessageChannel output();
}
