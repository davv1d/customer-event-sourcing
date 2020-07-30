package com.davv1d.customerevents.command;

import com.davv1d.customerevents.domain.CustomerState;
import lombok.Value;

import java.time.Instant;
import java.util.UUID;

@Value
public class CreateCommand implements Command {
    UUID uuid;
    String name;
    String email;
    CustomerState state;
    Instant when;
}
