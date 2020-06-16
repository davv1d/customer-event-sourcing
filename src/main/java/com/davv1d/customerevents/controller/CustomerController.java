package com.davv1d.customerevents.controller;

import com.davv1d.customerevents.command.ActivateCommand;
import com.davv1d.customerevents.command.ChangeNameCommand;
import com.davv1d.customerevents.command.CreateCommand;
import com.davv1d.customerevents.command.DeactivateCommand;
import com.davv1d.customerevents.service.CustomerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping("/create")
    public void create(@RequestBody CreateCommand command) {
        customerService.create(command);
    }

    @PutMapping("/activate")
    public void activate(@RequestBody ActivateCommand command) {
        customerService.activate(command)
        .onSuccess(log::info)
        .onFailure(throwable -> log.error(throwable.getMessage()));
    }

    @PutMapping("/deactivate")
    public void deactivate(@RequestBody DeactivateCommand command) {
        customerService.deactivate(command);
    }

    @PutMapping("/change-name")
    public void activate(@RequestBody ChangeNameCommand command) {
        customerService.changeName(command);
    }
}
