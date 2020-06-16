package com.davv1d.customerevents.domain;

import com.davv1d.customerevents.command.*;
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
import java.util.function.Function;
import java.util.function.Supplier;

import static com.davv1d.customerevents.domain.CustomerState.*;
import static javaslang.API.Case;
import static javaslang.collection.List.ofAll;

@RequiredArgsConstructor
@With(value = AccessLevel.PRIVATE)
public class Customer {
    @Getter
    private final UUID uuid;
    @Getter
    private final String name;
    @Getter
    private final CustomerState state;
    private final ImmutableList<DomainEvent> changes;
    private final CustomerEventCreator customerEventCreator = new CustomerEventCreator();

    public Customer(CreateCommand command) {
        CustomerCreated event = (CustomerCreated) customerEventCreator.getEvent(command);
        this.uuid = event.getUuid();
        this.name = event.getName();
        this.state = event.getState();
        this.changes = ImmutableList.of(event);
    }

    public Customer(DomainEvent event) {
        CustomerCreated event1 = (CustomerCreated) event;
        this.uuid = event1.getUuid();
        this.name = event1.getName();
        this.state = event1.getState();
        this.changes = ImmutableList.of(event1);
    }


    private final Function<DomainEvent, Customer> activated = (event) -> this.withState(ACTIVATED);

    private final Function<DomainEvent, Customer> deactivated = (event) -> this.withState(DEACTIVATED);

    private final Function<DomainEvent, Customer> changedName = (event) -> this.withName(((CustomerNameChanged) event).getName());

    private Customer patternMatch(final DomainEvent event) {
        return API.Match(event.getType()).of(
                Case((t) -> t.equals(CustomerActivated.TYPE), () -> activated.apply(event)),
                Case((t) -> t.equals(CustomerDeactivated.TYPE), () -> deactivated.apply(event)),
                Case((t) -> t.equals(CustomerNameChanged.TYPE), () -> changedName.apply(event)),
                Case((t) -> t.equals(CustomerCreated.TYPE), () -> new Customer(event))
        );
    }

    private final Function<DomainEvent, Customer> appendChange = (event) -> this.patternMatch(event).addEvent(event);

    public static Customer rebuild(UUID uuid, List<DomainEvent> history) {
        return ofAll(history)
                .foldLeft(new Customer(uuid, "", INITIALIZED, ImmutableList.of()), Customer::patternMatch)
                .markChangesAsCommitted();
    }

    private Customer addEvent(DomainEvent event) {
        ImmutableList<DomainEvent> domainEvents = ImmutableList.<DomainEvent>builder()
                .addAll(changes)
                .add(event)
                .build();
        return this.withChanges(domainEvents);
    }

    public Try<Customer> activate(ActivateCommand command) {
        return makeCommand(command, () -> !this.isActivate(), "Customer is activate");
    }

    public Try<Customer> deactivate(DeactivateCommand command) {
        return makeCommand(command, () -> !this.isDeactivate(), "Customer is deactivate");
    }

    public Try<Customer> changeNameTo(ChangeNameCommand command) {
        return makeCommand(command, this::isActivate, "Customer is deactivate");
    }

    private Try<Customer> makeCommand(Command command, Supplier<Boolean> condition, String errorMessage) {
        return Try.of(() -> {
            if (condition.get()) {
                return appendChange.apply(customerEventCreator.getEvent(command));
            } else {
                throw new IllegalStateException(errorMessage);
            }
        });
    }

    public boolean isActivate() {
        return this.state == ACTIVATED;
    }

    public boolean isDeactivate() {
        return this.state == DEACTIVATED;
    }

    public ImmutableList<DomainEvent> getUncommittedChanges() {
        return changes;
    }

    public Customer markChangesAsCommitted() {
        return this.withChanges(ImmutableList.of());
    }
}