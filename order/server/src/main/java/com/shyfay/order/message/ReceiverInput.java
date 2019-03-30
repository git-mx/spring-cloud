package com.shyfay.order.message;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

/**
 * @author mx
 * @since 2019/3/30
 */
public interface ReceiverInput {
    String INPUT = "input";

    @Input(ReceiverInput.INPUT)
    SubscribableChannel input();

}
