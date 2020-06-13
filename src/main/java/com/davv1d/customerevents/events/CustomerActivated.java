package com.davv1d.customerevents.events;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerActivated implements DomainEvent {
    public static final String TYPE = "customer.activated";
    private UUID uuid;
    private Instant when;

    @Override
    public String type() {
        return TYPE;
    }

    @Override
    public UUID aggregateUuid() {
        return uuid;
    }

    @Override
    public Instant when() {
        return when;
    }
}
