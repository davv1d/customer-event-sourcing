package com.davv1d.customerevents.events;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import java.time.Instant;
import java.util.UUID;


@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(name = CustomerActivated.TYPE, value = CustomerActivated.class),
        @JsonSubTypes.Type(name = CustomerNameChanged.TYPE, value = CustomerNameChanged.class),
        @JsonSubTypes.Type(name = CustomerDeactivated.TYPE, value = CustomerDeactivated.class)
})
public interface DomainEvent {
    String type();
    Instant when();
    UUID aggregateUuid();
}
