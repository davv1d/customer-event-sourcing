package com.davv1d.customerevents.errors;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Violation {
    private final String field;
    private final String message;
}
