package com.davv1d.customerevents.service;

import com.davv1d.customerevents.command.ActivateCommand;
import com.davv1d.customerevents.command.ChangeNameCommand;
import com.davv1d.customerevents.command.CreateCommand;
import com.davv1d.customerevents.command.DeactivateCommand;
import com.davv1d.customerevents.domain.Customer;
import com.davv1d.customerevents.repository.CustomerRepository;
import javaslang.control.Try;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.function.Function;

@Slf4j
@Service
public class CustomerService {
    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public void create(CreateCommand command) {
        Customer customer = new Customer(command);
        customerRepository.save(customer);
    }

    public Try<String> activate(ActivateCommand command) {
//        customerRepository.getByUUID(command.getUuid())
//                .ifPresent(customer -> {
//                    Try<Customer> customers = customer.activate(command)
//                            .onSuccess(customerRepository::save)
//                            .onFailure(throwable -> log.error(throwable.getMessage()));
//                });
        String successMessage = "Customer with uuid " + command.getUuid() + " is activate";
        return pattern(command.getUuid(), customer -> customer.activate(command), successMessage);
    }

    public Try<String> deactivate(DeactivateCommand command) {
        String successMessage = "Customer with uuid " + command.getUuid() + " is deactivate";
        return pattern(command.getUuid(), customer -> customer.deactivate(command), successMessage);
    }

    public Try<String> changeName(ChangeNameCommand command) {
        String successMessage = "Customer with uuid " + command.getUuid() + " name is " + command.getName();
        return pattern(command.getUuid(), customer -> customer.changeNameTo(command), successMessage);
    }

    private Try<String> pattern(UUID uuid, Function<Customer, Try<Customer>> action, String successMessage) {
        Try<Customer> customerTry = customerRepository.getByUUID(uuid);
        return customerTry
                .flatMap(action)
                .map(customer -> {
                    customerRepository.save(customer);
                    return successMessage;
                });
    }
}
