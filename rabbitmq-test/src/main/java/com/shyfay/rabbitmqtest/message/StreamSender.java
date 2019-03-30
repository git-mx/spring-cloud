package com.shyfay.rabbitmqtest.message;

import com.shyfay.rabbitmqtest.utils.Sender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

/**
 * @author mx
 * @since 2019/3/29
 */
@Component
@EnableBinding(Sender.class)
public class StreamSender {

    @Autowired
    @Qualifier("output")
    MessageChannel output;

    public void sendMessage(Object message){
        output.send(MessageBuilder.withPayload(message).build());
    }

}
