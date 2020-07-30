package com.davv1d.customerevents.errors;

import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@ControllerAdvice
public class ErrorsHandlingControllerAdvice {
    @ExceptionHandler(BindException.class)
    @ResponseStatus(BAD_REQUEST)
    @ResponseBody
    public ValidationErrorResponse nullValueInParameters(BindException e) {
        List<Violation> collect = e.getFieldErrors().stream()
                .map(fieldError -> new Violation(fieldError.getField(), fieldError.getDefaultMessage()))
                .collect(Collectors.toList());
        return new ValidationErrorResponse(collect);
    }
}
