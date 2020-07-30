package com.davv1d.customerevents.controller;

import com.davv1d.customerevents.command.CreateCommand;
import com.davv1d.customerevents.command.dto.ChangeNameCommandDto;
import com.davv1d.customerevents.command.dto.CreateCommandDto;
import com.davv1d.customerevents.mapper.CommandMapper;
import com.davv1d.customerevents.service.CustomerService;
import javaslang.control.Try;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindException;
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
    public void create(@RequestBody @Valid CreateCommandDto commandDto) throws BindException {
        Try<CreateCommand> createCommands = customerService.create(commandMapper.mapToCreateCommand(commandDto));
        if (createCommands.isFailure()) {
            throw (BindException) createCommands.getCause();
        }
    }

    @PutMapping(value = "/activate", params = "uuid")
    public void activate(@RequestParam UUID uuid) {
        customerService.activate(commandMapper.mapToActivateCommand(uuid))
                .onSuccess(log::info)
                .onFailure(throwable -> log.error(throwable.getMessage()));
    }

    @PutMapping(value = "/deactivate", params = "uuid")
    public void deactivate(@RequestParam UUID uuid) {
        customerService.deactivate(commandMapper.mapToDeactivateCommand(uuid))
                .onSuccess(log::info)
                .onFailure(throwable -> log.error(throwable.getMessage()));
    }

    @PutMapping("/change-name")
    public void activate(@RequestBody ChangeNameCommandDto commandDto) {
        customerService.changeName(commandMapper.mapToChangeNameCommand(commandDto))
                .onSuccess(log::info)
                .onFailure(throwable -> log.error(throwable.getMessage()));
    }
}
