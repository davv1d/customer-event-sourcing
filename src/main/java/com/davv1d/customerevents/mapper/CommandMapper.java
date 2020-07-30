package com.davv1d.customerevents.mapper;

import com.davv1d.customerevents.command.ActivateCommand;
import com.davv1d.customerevents.command.ChangeNameCommand;
import com.davv1d.customerevents.command.CreateCommand;
import com.davv1d.customerevents.command.DeactivateCommand;
import com.davv1d.customerevents.command.dto.ChangeNameCommandDto;
import com.davv1d.customerevents.command.dto.CreateCommandDto;
import com.davv1d.customerevents.domain.CustomerState;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.UUID;

@Component
public class CommandMapper {
    public ActivateCommand mapToActivateCommand(UUID uuid) {
        return new ActivateCommand(uuid, Instant.now());
    }

    public DeactivateCommand mapToDeactivateCommand(UUID uuid) {
        return new DeactivateCommand(uuid, Instant.now());
    }

    public ChangeNameCommand mapToChangeNameCommand(ChangeNameCommandDto changeNameCommandDto) {
        return new ChangeNameCommand(changeNameCommandDto.getUuid(), changeNameCommandDto.getName(), Instant.now());
    }

    public CreateCommand mapToCreateCommand(CreateCommandDto createCommandDto) {
        return new CreateCommand(UUID.randomUUID(),createCommandDto.getName(), createCommandDto.getEmail(), CustomerState.INITIALIZED, Instant.now());
    }
}
