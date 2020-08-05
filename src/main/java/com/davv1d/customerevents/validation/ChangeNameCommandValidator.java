package com.davv1d.customerevents.validation;

import com.davv1d.customerevents.command.ChangeNameCommand;
import com.davv1d.customerevents.repository.NameRepository;
import com.davv1d.customerevents.validation.conditions.Condition;

import java.util.Collections;
import java.util.List;

public class ChangeNameCommandValidator extends Validator<ChangeNameCommand> {
    private final NameRepository nameRepository;

    public ChangeNameCommandValidator(NameRepository nameRepository) {
        this.nameRepository = nameRepository;
    }

    @Override
    protected List<Condition<ChangeNameCommand>> get(ChangeNameCommand value) {
        return Collections.emptyList();
    }
}
