package com.davv1d.customerevents.events;

import com.davv1d.customerevents.domain.CustomerState;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
public class CustomerCreated implements DomainEvent {
    public static final String TYPE = "customer.created";
    private UUID uuid;
    @Getter
    private String name;
    @Getter
    private CustomerState state;
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
