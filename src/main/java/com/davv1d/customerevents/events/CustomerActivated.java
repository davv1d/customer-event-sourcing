package com.davv1d.customerevents.events;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
public class CustomerActivated implements DomainEvent {
    public static final String TYPE = "customer.activated";
    private UUID uuid;
    private Instant when;

    @Override
    public String getType() {
        return TYPE;
    }

    @Override
    public UUID getUuid() {
        return uuid;
    }

    @Override
    public Instant getWhen() {
        return when;
    }
}
