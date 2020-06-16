package com.davv1d.customerevents.service;

import com.davv1d.customerevents.domain.Customer;
import com.davv1d.customerevents.events.DomainEvent;
import com.davv1d.customerevents.events.entity.EventDescriptor;
import com.davv1d.customerevents.mapper.EventSerializer;
import com.davv1d.customerevents.publisher.PublishChannel;
import com.davv1d.customerevents.repository.CustomerRepository;
import com.davv1d.customerevents.repository.EventStreamRepository;
import javaslang.API;
import javaslang.control.Try;
import javassist.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

import static java.util.stream.Collectors.toList;

@Service
public class EventSourcedCustomerRepository implements CustomerRepository {
    private final EventStreamRepository eventStreamRepository;
    private final EventSerializer eventSerializer;

    public EventSourcedCustomerRepository(EventStreamRepository eventStreamRepository, EventSerializer eventSerializer) {
        this.eventStreamRepository = eventStreamRepository;
        this.eventSerializer = eventSerializer;
    }

    public void save(Customer customer) {
        List<DomainEvent> changes = customer.getUncommittedChanges();
        List<EventDescriptor> events = eventSerializer.serialize(changes);
        eventStreamRepository.saveEvents(customer.getUuid(), events);
        customer.markChangesAsCommitted();
    }

    public Try<Customer> getByUUID(UUID uuid) {
        List<DomainEvent> events = getRelatedEvents(uuid);
        return API.Match(events.isEmpty()).of(
                API.Case(true, Try.failure(new NotFoundException("Not found customer by uuid " + uuid))),
                API.Case(false, Try.success(Customer.rebuild(uuid, events)))
        );
    }

    private List<DomainEvent> getRelatedEvents(UUID uuid) {
        return eventStreamRepository.getEventsForAggregate(uuid)
                .stream()
                .map(eventSerializer::deserialize)
                .collect(toList());
    }
}
