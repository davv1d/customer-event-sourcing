package com.davv1d.customerevents.validation;

import com.davv1d.customerevents.command.ChangeNameCommand;
import com.davv1d.customerevents.domain.Name;
import com.davv1d.customerevents.repository.NameRepository;
import javaslang.control.Try;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;

import java.time.Instant;
import java.util.UUID;

import static org.junit.Assert.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ChangeNameValidatorTest {

    @Autowired
    private Validator<ChangeNameCommand> changeNameCommandValidator;

    @Autowired
    private NameRepository nameRepository;

    @AfterEach
    public void cleanUp() {
        nameRepository.deleteAll();
    }

    @Test
    @DisplayName("Should return error - name exists in db")
    public void shouldReturnErrorNameExistsInDb() {
        //Given
        FieldError nameError = new FieldError("ChangeNameCommand", "name", "name exists in database");
        Name name = new Name("test name");
        nameRepository.save(name);
        ChangeNameCommand changeNameCommand = new ChangeNameCommand(UUID.randomUUID(), "test name", Instant.now());
        //When
        Try<ChangeNameCommand> valid = changeNameCommandValidator.valid(changeNameCommand);
        //Then
        assertTrue(valid.isFailure());
        assertTrue(((BindException) valid.getCause()).getAllErrors().contains(nameError));
        assertEquals(1, ((BindException) valid.getCause()).getFieldErrors().size());
    }

    @Test
    @DisplayName("Should return error (letters size) - name exists in db")
    public void shouldReturnErrorLettersSizeNameExistsInDb() {
        //Given
        FieldError nameError = new FieldError("ChangeNameCommand", "name", "name exists in database");
        Name name = new Name("TEST name");
        nameRepository.save(name);
        ChangeNameCommand changeNameCommand = new ChangeNameCommand(UUID.randomUUID(), "test name", Instant.now());
        //When
        Try<ChangeNameCommand> valid = changeNameCommandValidator.valid(changeNameCommand);
        //Then
        assertTrue(valid.isFailure());
        assertTrue(((BindException) valid.getCause()).getAllErrors().contains(nameError));
        assertEquals(1, ((BindException) valid.getCause()).getFieldErrors().size());
    }

    @Test
    @DisplayName("Should return success")
    public void shouldReturnErrorSuccess() {
        //Given
        ChangeNameCommand changeNameCommand = new ChangeNameCommand(UUID.randomUUID(), "test name", Instant.now());
        //When
        Try<ChangeNameCommand> valid = changeNameCommandValidator.valid(changeNameCommand);
        //Then
        assertTrue(valid.isSuccess());
    }
}
