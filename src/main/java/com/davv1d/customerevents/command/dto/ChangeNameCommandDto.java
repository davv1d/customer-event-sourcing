package com.davv1d.customerevents.command.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ChangeNameCommandDto {
    private UUID uuid;
    private String name;
}
