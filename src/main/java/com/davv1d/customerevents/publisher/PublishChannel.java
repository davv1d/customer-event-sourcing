package com.davv1d.customerevents.publisher;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.integration.annotation.Publisher;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@EnableBinding(Source.class)
public class PublishChannel {

    @Publisher(channel = Source.OUTPUT)
    public String sendEvent(String payload, @Header UUID uuid) {
        return payload;
    }
}
