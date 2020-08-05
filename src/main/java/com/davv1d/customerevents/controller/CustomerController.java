package com.davv1d.customerevents.controller;

import com.davv1d.customerevents.command.CreateCommand;
import com.davv1d.customerevents.command.dto.ChangeNameCommandDto;
import com.davv1d.customerevents.command.dto.CreateCommandDto;
import com.davv1d.customerevents.mapper.CommandMapper;
import com.davv1d.customerevents.service.CustomerService;
import javaslang.control.Try;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

@Slf4j
@RestController
public class CustomerController {

    private final CommandMapper commandMapper;
    private final CustomerService customerService;

    public CustomerController(CommandMapper commandMapper, CustomerService customerService) {
        this.commandMapper = commandMapper;
        this.customerService = customerService;
    }

    @PostMapping("/create")
    public void create(@RequestBody @Valid CreateCommandDto commandDto) throws Throwable {
        Try<CreateCommand> validCommand = customerService.create(commandMapper.mapToCreateCommand(commandDto));
        writeInLogsAndThrow(validCommand);
    }

    @PutMapping(value = "/activate", params = "uuid")
    public void activate(@RequestParam UUID uuid) throws Throwable {
        Try<String> activate = customerService.activate(commandMapper.mapToActivateCommand(uuid));
        writeInLogsAndThrow(activate);
    }

    @PutMapping(value = "/deactivate", params = "uuid")
    public void deactivate(@RequestParam UUID uuid) throws Throwable {
        Try<String> deactivate = customerService.deactivate(commandMapper.mapToDeactivateCommand(uuid));
        writeInLogsAndThrow(deactivate);
    }

    @PutMapping("/change-name")
    public void changeName(@RequestBody ChangeNameCommandDto commandDto) throws Throwable {
        Try<String> validCommand = customerService.changeName(commandMapper.mapToChangeNameCommand(commandDto));
        writeInLogsAndThrow(validCommand);
    }

    private <T> void writeInLogsAndThrow(Try<T> validCommand) throws Throwable {
        if (validCommand.isFailure()) {
            log.error(validCommand.getCause().getMessage());
            throw validCommand.getCause();
        } else {
            log.info(validCommand.get().toString());
        }
    }
}
