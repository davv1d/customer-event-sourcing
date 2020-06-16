package com.davv1d.customerevents.domain;

import com.davv1d.customerevents.command.*;
import com.davv1d.customerevents.events.*;

import java.time.Instant;
import java.util.UUID;

public class CustomerEventCreator {
    private CustomerActivated activateEvent(Command command) {
        ActivateCommand activateCommand = (ActivateCommand) command;
        return new CustomerActivated(activateCommand.getUuid(), Instant.now());
    }

    private CustomerDeactivated deactivateEvent(Command command) {
        DeactivateCommand deactivateCommand = (DeactivateCommand) command;
        return new CustomerDeactivated(deactivateCommand.getUuid(), Instant.now());
    }

    private CustomerNameChanged changedNameEvent(Command command) {
        ChangeNameCommand changeNameCommand = (ChangeNameCommand) command;
        return new CustomerNameChanged(changeNameCommand.getUuid(), changeNameCommand.getName(), Instant.now());
    }

    private CustomerCreated createdCustomerEvent(Command command) {
        CreateCommand createCommand = (CreateCommand) command;
        UUID randomUUID = UUID.randomUUID();
        return new CustomerCreated(randomUUID, createCommand.getName(), CustomerState.INITIALIZED, Instant.now());
    }

    public DomainEvent getEvent(Command command) {
        if (command instanceof ActivateCommand) {
            return activateEvent(command);
        } else if (command instanceof DeactivateCommand) {
            return deactivateEvent(command);
        } else if (command instanceof ChangeNameCommand) {
            return changedNameEvent(command);
        } else if (command instanceof CreateCommand) {
            return createdCustomerEvent(command);
        }
        throw new IllegalStateException("Don't match");
    }
}
