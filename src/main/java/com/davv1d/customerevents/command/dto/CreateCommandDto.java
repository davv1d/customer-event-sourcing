package com.davv1d.customerevents.command.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class CreateCommandDto {
    @NotEmpty
    private String name;
    @NotEmpty
    private String email;
}
