package com.davv1d.customerevents.validation.conditions;

import lombok.Getter;
import org.springframework.validation.ObjectError;

import java.util.function.Predicate;

@Getter
public class Condition<T> {
    private final T valueToCheck;
    private final ObjectError objectError;
    private final Predicate<T> test;

    public Condition(T valueToCheck, ObjectError objectError, Predicate<T> test) {
        this.valueToCheck = valueToCheck;
        this.objectError = objectError;
        this.test = test;
    }
}
