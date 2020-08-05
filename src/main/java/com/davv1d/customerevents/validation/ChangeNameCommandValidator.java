package com.davv1d.customerevents.validation;

import com.davv1d.customerevents.command.ChangeNameCommand;
import com.davv1d.customerevents.repository.EventStreamRepository;
import com.davv1d.customerevents.repository.NameRepository;
import com.davv1d.customerevents.validation.conditions.Condition;
import org.springframework.validation.FieldError;

import java.util.Arrays;
import java.util.List;

public class ChangeNameCommandValidator extends Validator<ChangeNameCommand> {
    private final NameRepository nameRepository;
    private final EventStreamRepository eventStreamRepository;

    public ChangeNameCommandValidator(NameRepository nameRepository, EventStreamRepository eventStreamRepository) {
        this.nameRepository = nameRepository;
        this.eventStreamRepository = eventStreamRepository;
    }

    @Override
    protected List<Condition<ChangeNameCommand>> get(ChangeNameCommand value) {
        FieldError nameError = new FieldError("ChangeNameCommand", "name", "name exists in database");
        FieldError uuidError = new FieldError("ChangeNameCommand", "uuid", "uuid does not exist in database");
        Condition<ChangeNameCommand> nameCondition = new Condition<>(value, nameError, command -> !nameRepository.existsByNameIgnoreCase(command.getName()));
        Condition<ChangeNameCommand> uuidCondition = new Condition<>(value, uuidError, command -> eventStreamRepository.existsByAggregateUUID(command.getUuid()));
        return Arrays.asList(nameCondition, uuidCondition);
    }
}
