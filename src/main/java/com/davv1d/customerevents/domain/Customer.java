package com.davv1d.customerevents.domain;

import com.davv1d.customerevents.events.*;
import com.google.common.collect.ImmutableList;
import lombok.Data;

import java.time.Instant;
import java.util.*;

import static javaslang.collection.List.*;

@Data
public class Customer {
    private final UUID uuid;
    private String name = "";
    private CustomerState state = CustomerState.INITIALIZED;
    private List<DomainEvent> changes = new ArrayList<>();

    public Customer(UUID uuid) {
        this.uuid = uuid;
    }

    public static Customer recreateFrom(UUID uuid, List<DomainEvent> domainEvents) {
        return ofAll(domainEvents).foldLeft(new Customer(uuid), Customer::handleEvent);
    }

    private static Customer handleEvent(Customer customer, DomainEvent domainEvent) {
        switch (domainEvent.type()) {
            case CustomerActivated.TYPE:
                return customer.changeOnActivated();
            case CustomerDeactivated.TYPE:
                return customer.changeOnDeactivate();
            case CustomerNameChanged.TYPE:
                return customer.changeOnNameChanged((CustomerNameChanged) domainEvent);
            default:
                return null;
        }
    }

    private Customer changeOnNameChanged(CustomerNameChanged customerNameChanged) {
        this.name = customerNameChanged.getName();
        return this;
    }

    private Customer changeOnDeactivate() {
        this.state = CustomerState.DEACTIVATED;
        return this;
    }

    private Customer changeOnActivated() {
        this.state = CustomerState.ACTIVATED;
        return this;
    }

    public void activate() {
        if (isActivate()) {
            throw new IllegalStateException();
        }
        customerActivated(new CustomerActivated(uuid, Instant.now()));
    }

    private void customerActivated(CustomerActivated customerActivated) {
        state = CustomerState.ACTIVATED;
        changes.add(customerActivated);
    }

    public void deactivate() {
        if (isDeactivate()) {
            throw new IllegalStateException();
        }
        customerDeactivated(new CustomerDeactivated(uuid, Instant.now()));
    }

    private void customerDeactivated(CustomerDeactivated customerDeactivated) {
        state = CustomerState.DEACTIVATED;
        changes.add(customerDeactivated);
    }

    public void changeNameTo(String name) {
        if (isDeactivate()) {
            throw new IllegalStateException();
        }
        customerNameChanged(new CustomerNameChanged(uuid, name, Instant.now()));
    }

    private void customerNameChanged(CustomerNameChanged customerNameChanged) {
        this.name = customerNameChanged.getName();
        changes.add(customerNameChanged);
    }

    public boolean isActivate() {
        return this.state == CustomerState.ACTIVATED;
    }

    public boolean isDeactivate() {
        return this.state == CustomerState.DEACTIVATED;
    }

    public String getName() {
        return this.name;
    }

    public List<DomainEvent> getChanges() {
        return ImmutableList.copyOf(changes);
    }

    public void flushChanges() {
        this.changes.clear();
    }
}
