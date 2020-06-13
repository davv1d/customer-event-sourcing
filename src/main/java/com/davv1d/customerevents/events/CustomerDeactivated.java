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
public class CustomerDeactivated implements DomainEvent {
    public static final String TYPE = "customer.deactivate";
    private UUID uuid;
    private Instant when;

    @Override
    public String type() {
        return TYPE;
    }

    @Override
    public Instant when() {
        return when;
    }

    @Override
    public UUID aggregateUuid() {
        return uuid;
    }
}
