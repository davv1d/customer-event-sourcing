package com.davv1d.customerevents.service;

import com.davv1d.customerevents.command.ActivateCommand;
import com.davv1d.customerevents.command.ChangeNameCommand;
import com.davv1d.customerevents.command.CreateCommand;
import com.davv1d.customerevents.command.DeactivateCommand;
import javaslang.control.Try;

public interface CustomerService {
    Try<CreateCommand> create(CreateCommand command);
    Try<String> activate(ActivateCommand command);
    Try<String> deactivate(DeactivateCommand command);
    Try<String> changeName(ChangeNameCommand command);
}
