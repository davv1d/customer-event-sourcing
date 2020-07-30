package com.davv1d.customerevents.errors;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class ValidationErrorResponse {
    private final List<Violation> violations;
}
