package com.davv1d.customerevents.validation.conditions;

import lombok.Getter;

import java.util.function.Predicate;

@Getter
public class Condition<T> {
    private final T valueToCheck;
    private final String checkedField;
    private final String errorMessage;
    private final Predicate<T> test;

    public Condition(T valueToCheck, String checkedField, String errorMessage, Predicate<T> test) {
        this.valueToCheck = valueToCheck;
        this.checkedField = checkedField;
        this.errorMessage = errorMessage;
        this.test = test;
    }
}
