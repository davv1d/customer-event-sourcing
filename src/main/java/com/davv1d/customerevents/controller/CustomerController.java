package com.davv1d.customerevents.controller;

import com.davv1d.customerevents.domain.Customer;
import com.davv1d.customerevents.service.EventSourcedCustomerService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
public class CustomerController {

    private final EventSourcedCustomerService eventSourcedCustomerService;

    public CustomerController(EventSourcedCustomerService eventSourcedCustomerService) {
        this.eventSourcedCustomerService = eventSourcedCustomerService;
    }

    @GetMapping("/test/{uuid}")
    public void test(@PathVariable String uuid) {
//        UUID uuid = UUID.randomUUID();
        UUID uuid1 = UUID.fromString(uuid);
        Customer customer = new Customer(uuid1);
//        customer.activate();
        customer.changeNameTo("Gowno");
        customer.activate();
        eventSourcedCustomerService.save(customer);
    }
}
