package com.davv1d.customerevents.command;

import lombok.Value;

import java.time.Instant;
import java.util.UUID;

@Value
public class ChangeNameCommand implements Command{
    UUID uuid;
    String name;
    Instant when;
}
