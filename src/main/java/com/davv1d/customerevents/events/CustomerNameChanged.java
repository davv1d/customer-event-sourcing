package com.davv1d.customerevents.events;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
public class CustomerNameChanged implements DomainEvent{
    public static final String TYPE = "customer.nameChanged";
    private UUID uuid;
    @Getter
    private String name;
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
