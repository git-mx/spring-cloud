package com.shyfay.order.message;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

/**
 * @author mx
 * @since 2019/3/30
 */
@Component
@EnableBinding(SenderOutput.class)
public class MessageSender {

    @Autowired
    @Qualifier(SenderOutput.OUTPUT)
    MessageChannel output;

    public void send(Object message){
        output.send(MessageBuilder.withPayload(message).build());
    }

}
