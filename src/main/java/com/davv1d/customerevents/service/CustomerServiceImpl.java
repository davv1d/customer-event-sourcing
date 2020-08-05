package com.davv1d.customerevents.service;

import com.davv1d.customerevents.command.ActivateCommand;
import com.davv1d.customerevents.command.ChangeNameCommand;
import com.davv1d.customerevents.command.CreateCommand;
import com.davv1d.customerevents.command.DeactivateCommand;
import com.davv1d.customerevents.domain.Customer;
import com.davv1d.customerevents.domain.Email;
import com.davv1d.customerevents.domain.Name;
import com.davv1d.customerevents.repository.CustomerRepository;
import com.davv1d.customerevents.repository.EmailRepository;
import com.davv1d.customerevents.repository.NameRepository;
import com.davv1d.customerevents.validation.Validator;
import javaslang.control.Try;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.function.Function;

@Slf4j
@Service
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;
    private final NameRepository nameRepository;
    private final EmailRepository emailRepository;
    private final Validator<CreateCommand> createCommandValidator;

    public CustomerServiceImpl(CustomerRepository customerRepository, NameRepository nameRepository, EmailRepository emailRepository, Validator<CreateCommand> createCommandValidator) {
        this.customerRepository = customerRepository;
        this.nameRepository = nameRepository;
        this.emailRepository = emailRepository;
        this.createCommandValidator = createCommandValidator;
    }

    public Try<CreateCommand> create(CreateCommand command) {
        return createCommandValidator.valid(command)
                .onSuccess(command1 -> {
                    Customer customer = new Customer(command);
                    nameRepository.save(new Name(command.getName()));
                    emailRepository.save(new Email(command.getEmail()));
                    customerRepository.save(customer);
                });
    }

    public Try<String> activate(ActivateCommand command) {
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
