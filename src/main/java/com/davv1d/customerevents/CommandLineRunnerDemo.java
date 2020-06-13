package com.davv1d.customerevents;

import com.davv1d.customerevents.domain.Customer;
import com.davv1d.customerevents.events.entity.EventStream;
import com.davv1d.customerevents.mapper.EventSerializer;
import com.davv1d.customerevents.repository.EventStreamRepository;
import com.davv1d.customerevents.service.EventSourcedCustomerService;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
public class CommandLineRunnerDemo implements org.springframework.boot.CommandLineRunner {

    final EventSerializer eventSerializer;
    final EventStreamRepository eventStreamRepository;
    final EventSourcedCustomerService eventSourcedCustomerService;

    public CommandLineRunnerDemo(EventSerializer eventSerializer, EventStreamRepository eventStreamRepository, EventSourcedCustomerService eventSourcedCustomerService) {
        this.eventSerializer = eventSerializer;
        this.eventStreamRepository = eventStreamRepository;
        this.eventSourcedCustomerService = eventSourcedCustomerService;
    }

    @Override
    public void run(String... args) throws Exception {
//        UUID uuid = UUID.randomUUID();
//        Customer customer = new Customer(uuid);
////        customer.activate();
//        customer.changeNameTo("Gowno command");
//        eventSourcedCustomerService.save(customer);
//        Optional<EventStream> byAggregateUUID = eventStreamRepository.findByAggregateUUID(uuid);
//        System.out.println(byAggregateUUID);
//        Customer byUUID = eventSourcedCustomerService.getByUUID(uuid);
//        System.out.println(byUUID);
//        EventStream eventStream = new EventStream(uuid);
//        EventStream savedEventStream = eventStreamRepository.save(eventStream);
//        CustomerNameChanged customerNameChanged = new CustomerNameChanged(uuid, "test name", Instant.now());
//        EventDescriptor eventDescriptor = eventSerializer.serialize(customerNameChanged);
//        eventDescriptorRepository.save(eventDescriptor);
//        Optional<EventStream> byId = eventStreamRepository.findById(savedEventStream.getId());
//        System.out.println(byId);
    }
}
