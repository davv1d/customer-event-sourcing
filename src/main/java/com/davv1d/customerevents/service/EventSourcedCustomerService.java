package com.davv1d.customerevents.service;

import com.davv1d.customerevents.domain.Customer;
import com.davv1d.customerevents.events.DomainEvent;
import com.davv1d.customerevents.events.entity.EventDescriptor;
import com.davv1d.customerevents.events.entity.EventStream;
import com.davv1d.customerevents.mapper.EventSerializer;
import com.davv1d.customerevents.publisher.EventPublisher;
import com.davv1d.customerevents.repository.EventStreamRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@Service
public class EventSourcedCustomerService {
    private final EventPublisher eventPublisher;
    private final EventStreamRepository eventStreamRepository;
    private final EventSerializer eventSerializer;

    public EventSourcedCustomerService(EventPublisher eventPublisher, EventStreamRepository eventStreamRepository, EventSerializer eventSerializer) {
        this.eventPublisher = eventPublisher;
        this.eventStreamRepository = eventStreamRepository;
        this.eventSerializer = eventSerializer;
    }

    public void save(Customer customer) {
        List<DomainEvent> changes = customer.getChanges();
        List<EventDescriptor> eventDescriptors = changes.stream()
                .map(eventSerializer::serialize)
                .collect(Collectors.toList());
        EventStream eventStream = new EventStream(customer.getUuid());
        eventStream.addEvents(eventDescriptors);
        eventStreamRepository.save(eventStream);
        customer.flushChanges();
        eventDescriptors.forEach(eventDescriptor -> eventPublisher.sendEvent(eventDescriptor.getBody(), eventDescriptor.getAggregateUUID()));
    }

    public Customer getByUUID(UUID uuid) {
        return Customer.recreateFrom(uuid, getRelatedEvents(uuid));
    }

    private List<DomainEvent> getRelatedEvents(UUID uuid) {
        return eventStreamRepository.getEventsForAggregate(uuid)
                .stream()
                .map(eventSerializer::deserialize)
                .collect(toList());
    }

}
