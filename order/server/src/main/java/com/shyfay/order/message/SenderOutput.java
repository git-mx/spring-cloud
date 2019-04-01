package com.shyfay.order.message;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

/**
 * @author mx
 * @since 2019/3/30
 */
public interface SenderOutput {
    String OUTPUT = "output";

    @Output(SenderOutput.OUTPUT)
    MessageChannel output();
}
