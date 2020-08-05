package com.davv1d.customerevents.validation;

import com.davv1d.customerevents.command.CreateCommand;
import com.davv1d.customerevents.repository.EmailRepository;
import com.davv1d.customerevents.repository.NameRepository;
import com.davv1d.customerevents.validation.conditions.Condition;
import org.springframework.validation.FieldError;

import java.util.Arrays;
import java.util.List;

public class CreateCommandValidator extends Validator<CreateCommand> {
    private final NameRepository nameRepository;
    private final EmailRepository emailRepository;

    public CreateCommandValidator(NameRepository nameRepository, EmailRepository emailRepository) {
        this.nameRepository = nameRepository;
        this.emailRepository = emailRepository;
    }

    @Override
    public List<Condition<CreateCommand>> get(CreateCommand value) {
        FieldError nameError = new FieldError("CreateCommand", "name", "name exists in database");
        FieldError emailError = new FieldError("CreateCommand", "email", "email exists in database");
        Condition<CreateCommand> nameExists = new Condition<>(value, nameError, command -> !nameRepository.existsByNameIgnoreCase(command.getName()));
        Condition<CreateCommand> emailExists = new Condition<>(value, emailError, command -> !emailRepository.existsByEmailIgnoreCase(command.getEmail()));
        return Arrays.asList(nameExists, emailExists);
    }
}
