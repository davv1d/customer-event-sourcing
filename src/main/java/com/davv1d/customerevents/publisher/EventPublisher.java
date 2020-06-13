package com.davv1d.customerevents.publisher;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.integration.annotation.Publisher;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Slf4j
@Component
@EnableBinding(Source.class)
public class EventPublisher {

//    private final Source source;
//
//    public EventPublisher(Source source) {
//        this.source = source;
//    }
//
//    public void sendEvent(String payload) {
//        boolean send = source.output().send(MessageBuilder.withPayload(payload).build());
//        log.info("sent message: " + send);
//    }
    @Publisher(channel = Source.OUTPUT)
    public String sendEvent(String payload, @Header UUID uuid) {
        System.out.println(payload);
        return payload;
    }
}
