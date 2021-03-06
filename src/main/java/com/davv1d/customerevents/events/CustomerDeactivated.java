package com.davv1d.customerevents.events;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
public class CustomerDeactivated implements DomainEvent {
    public static final String TYPE = "customer.deactivate";
    private UUID uuid;
    private Instant when;

    @Override
    public String getType() {
        return TYPE;
    }

    @Override
    public Instant getWhen() {
        return when;
    }

    @Override
    public UUID getUuid() {
        return uuid;
    }
}
