package com.davv1d.customerevents.command;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ChangeNameCommand implements Command{
    private UUID uuid;
    private String name;
}
