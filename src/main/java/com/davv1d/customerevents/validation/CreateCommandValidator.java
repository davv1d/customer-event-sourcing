package com.davv1d.customerevents.validation;

import com.davv1d.customerevents.command.CreateCommand;
import com.davv1d.customerevents.repository.NameRepository;
import com.davv1d.customerevents.validation.conditions.Condition;
import org.springframework.validation.FieldError;

import java.util.Collections;
import java.util.List;

public class CreateCommandValidator extends Validator<CreateCommand> {
    private final NameRepository nameRepository;

    public CreateCommandValidator(NameRepository nameRepository) {
        this.nameRepository = nameRepository;
    }

    @Override
    public List<Condition<CreateCommand>> get(CreateCommand value) {
        FieldError nameError = new FieldError("CreateCommand", "name", "name exists in database");
        Condition<CreateCommand> nameIsExist = new Condition<>(value, "name","name exists in database", command -> !nameRepository.findByValue(command.getName()).isPresent());
        return Collections.singletonList(nameIsExist);
    }
}
