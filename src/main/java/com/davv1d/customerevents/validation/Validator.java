package com.davv1d.customerevents.validation;

import com.davv1d.customerevents.validation.conditions.Condition;
import javaslang.control.Try;
import org.springframework.validation.BindException;

import java.util.List;
import java.util.stream.Collectors;

public abstract class Validator<T> {

    private List<Condition<T>> getErrors(T value) {
        return get(value).stream()
                .filter(c -> !c.getTest().test(value))
                .collect(Collectors.toList());
    }

    public Try<T> valid(T value) {
        List<Condition<T>> errors = getErrors(value);
        if (errors.isEmpty()) {
            return Try.success(value);
        } else {
            return Try.failure(joinErrors(value, errors));
        }
    }

    private BindException joinErrors(T value, List<Condition<T>> errors) {
        BindException bindException = new BindException(value, value.getClass().getSimpleName());
        for (Condition<T> error : errors) {
            bindException.addError(error.getObjectError());
//            bindException.rejectValue(error.getCheckedField(), "", error.getErrorMessage());
        }
        return bindException;
    }

    protected abstract List<Condition<T>> get(T value);
}
