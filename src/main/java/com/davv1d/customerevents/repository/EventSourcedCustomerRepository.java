package com.davv1d.customerevents.repository;

import com.davv1d.customerevents.domain.Customer;
import com.davv1d.customerevents.events.DomainEvent;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class EventSourcedCustomerRepository implements CustomerRepository {
    private final Map<UUID, List<DomainEvent>> customersEvents = new ConcurrentHashMap<>();

    @Override
    public void save(Customer customer) {
        List<DomainEvent> newChanges = customer.getChanges();
        List<DomainEvent> currentChanges = customersEvents.getOrDefault(customer.getUuid(), new ArrayList<>());
        currentChanges.addAll(newChanges);
        customersEvents.put(customer.getUuid(), currentChanges);
        customer.flushChanges();
    }

    @Override
    public Customer findBy(UUID uuid) {
        if (!customersEvents.containsKey(uuid)) {
            return null;
        }
        return Customer.recreateFrom(uuid, customersEvents.get(uuid));
    }

    public Customer find(UUID uuid, Instant timestamp) {
        if (!customersEvents.containsKey(uuid)) {
            return null;
        }
        List<DomainEvent> domainEvents = customersEvents.get(uuid).stream()
                .filter(event -> event.when().isBefore(timestamp))
                .collect(Collectors.toList());
        return Customer.recreateFrom(uuid, domainEvents);
    }
}
