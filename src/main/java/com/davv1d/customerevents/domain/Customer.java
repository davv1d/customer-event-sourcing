package com.davv1d.customerevents.domain;

import com.davv1d.customerevents.command.ActivateCommand;
import com.davv1d.customerevents.command.ChangeNameCommand;
import com.davv1d.customerevents.command.CreateCommand;
import com.davv1d.customerevents.command.DeactivateCommand;
import com.davv1d.customerevents.events.*;
import com.google.common.collect.ImmutableList;
import javaslang.API;
import javaslang.control.Try;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.With;

import java.util.List;
import java.util.UUID;

import static com.davv1d.customerevents.domain.CustomerState.*;
import static javaslang.API.Case;
import static javaslang.Predicates.instanceOf;
import static javaslang.collection.List.ofAll;
import static javaslang.control.Try.failure;
import static javaslang.control.Try.success;

@RequiredArgsConstructor
@With(value = AccessLevel.PRIVATE)
public class Customer {
    @Getter
    private final UUID uuid;
    @Getter
    private final String name;
    @Getter
    private final String email;
    @Getter
    private final CustomerState state;
    private final ImmutableList<DomainEvent> changes;

    public Customer(CreateCommand command) {
        CustomerCreated event = handleCommand(command);
        this.uuid = event.getUuid();
        this.name = event.getName();
        this.email = event.getEmail();
        this.state = event.getState();
        this.changes = ImmutableList.of(event);
    }

    private CustomerActivated handleCommand(ActivateCommand command) {
        return new CustomerActivated(command.getUuid(), command.getWhen());
    }

    private CustomerDeactivated handleCommand(DeactivateCommand command) {
        return new CustomerDeactivated(command.getUuid(), command.getWhen());
    }

    private CustomerNameChanged handleCommand(ChangeNameCommand command) {
        return new CustomerNameChanged(command.getUuid(), command.getName(), command.getWhen());
    }

    private CustomerCreated handleCommand(CreateCommand command) {
        return new CustomerCreated(command.getUuid(), command.getName(), command.getEmail(), command.getState(), command.getWhen());
    }

    private Customer handleEvent(CustomerActivated event) {
        return this.withState(ACTIVATED);
    }

    private Customer handleEvent(CustomerDeactivated event) { return this.withState(DEACTIVATED); }

    private Customer handleEvent(CustomerNameChanged event) { return this.withName(event.getName()); }

    private Customer handleEvent(CustomerCreated event) {
        return new Customer(event.getUuid(), event.getName(), event.getEmail(), event.getState(), ImmutableList.of(event));
    }

    private Customer patternMatch(final DomainEvent event) {
        return API.Match(event).of(
                Case(instanceOf(CustomerActivated.class), this::handleEvent),
                Case(instanceOf(CustomerDeactivated.class), this::handleEvent),
                Case(instanceOf(CustomerNameChanged.class), this::handleEvent),
                Case(instanceOf(CustomerCreated.class), this::handleEvent)
        );
    }

    private Customer appendChange(DomainEvent event) {
        return this.patternMatch(event).addEvent(event);
    }

    public static Customer rebuild(UUID uuid, List<DomainEvent> history) {
        return ofAll(history)
                .foldLeft(getInitialState(uuid), Customer::patternMatch)
                .markChangesAsCommitted();
    }

    private static Customer getInitialState(UUID uuid) { return new Customer(uuid, "", "", INITIALIZED, ImmutableList.of()); }

    private Customer addEvent(DomainEvent event) {
        ImmutableList<DomainEvent> domainEvents = ImmutableList.<DomainEvent>builder().addAll(changes).add(event).build();
        return this.withChanges(domainEvents);
    }

    public Try<Customer> activate(ActivateCommand command) {
        return isActivate() ?
                failure(new IllegalStateException("Customer is activate")) :
                success(appendChange(handleCommand(command)));
    }

    public Try<Customer> deactivate(DeactivateCommand command) {
        return isDeactivate() ?
                failure(new IllegalStateException("Customer is deactivate")) :
                success(appendChange(handleCommand(command)));
    }

    public Try<Customer> changeNameTo(ChangeNameCommand command) {
        return isActivate() ?
                success(appendChange(handleCommand(command))) :
                failure(new IllegalStateException("Customer is not activate"));
    }

    public boolean isActivate() { return this.state == ACTIVATED; }

    public boolean isDeactivate() { return this.state == DEACTIVATED; }

    public ImmutableList<DomainEvent> getUncommittedChanges() { return changes; }

    public Customer markChangesAsCommitted() { return this.withChanges(ImmutableList.of()); }
}