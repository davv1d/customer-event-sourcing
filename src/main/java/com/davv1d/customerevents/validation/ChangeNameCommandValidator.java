package com.davv1d.customerevents.validation;

import com.davv1d.customerevents.command.ChangeNameCommand;
import com.davv1d.customerevents.repository.NameRepository;
import com.davv1d.customerevents.validation.conditions.Condition;
import org.springframework.validation.FieldError;

import java.util.Collections;
import java.util.List;

public class ChangeNameCommandValidator extends Validator<ChangeNameCommand> {
    private final NameRepository nameRepository;

    public ChangeNameCommandValidator(NameRepository nameRepository) {
        this.nameRepository = nameRepository;
    }

    @Override
    protected List<Condition<ChangeNameCommand>> get(ChangeNameCommand value) {
        FieldError nameError = new FieldError("ChangeNameCommand", "name", "name exists in database");
        Condition<ChangeNameCommand> nameCondition = new Condition<>(value, nameError, command -> !nameRepository.existsByNameIgnoreCase(command.getName()));
        return Collections.singletonList(nameCondition);
    }
}
