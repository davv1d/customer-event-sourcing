package com.davv1d.customerevents.mapper;

import com.davv1d.customerevents.events.DomainEvent;
import com.davv1d.customerevents.events.entity.EventDescriptor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

import static com.fasterxml.jackson.databind.DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES;
import static java.util.stream.Collectors.toList;

@Component
public class EventSerializer {

    private final ObjectMapper objectMapper;

    public EventSerializer() {
        this.objectMapper = new ObjectMapper();
        this.objectMapper.configure(FAIL_ON_UNKNOWN_PROPERTIES, false);
        this.objectMapper.registerModule(new JavaTimeModule());
        this.objectMapper.disable(SerializationFeature.WRITE_DATE_KEYS_AS_TIMESTAMPS);
    }

    public EventDescriptor serialize(DomainEvent event) {
        try {
            return new EventDescriptor(objectMapper.writeValueAsString(event), event.getWhen(), event.getType(), event.getUuid());
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }

    public DomainEvent deserialize(EventDescriptor eventDescriptor) {
        try {
            return objectMapper.readValue(eventDescriptor.getBody(), DomainEvent.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public List<EventDescriptor> serialize(List<DomainEvent> events) {
        return events.stream()
                .map(this::serialize)
                .collect(toList());
    }
}
