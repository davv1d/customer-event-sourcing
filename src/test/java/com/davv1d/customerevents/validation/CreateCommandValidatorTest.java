package com.davv1d.customerevents.validation;

import com.davv1d.customerevents.command.CreateCommand;
import com.davv1d.customerevents.domain.*;
import com.davv1d.customerevents.repository.*;
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
class CreateCommandValidatorTest {

    @Autowired
    private Validator<CreateCommand> createCommandValidator;

    @Autowired
    private NameRepository nameRepository;

    @Autowired
    private EmailRepository emailRepository;

    @AfterEach
    public void cleanUp() {
        nameRepository.deleteAll();
        emailRepository.deleteAll();
    }

    @Test
    @DisplayName("should return error - name exists in database")
    public void shouldReturnErrorNameExistsInDatabase() {
        //Given
        FieldError nameError = new FieldError("CreateCommand", "name", "name exists in database");
        Name name = new Name("test name");
        nameRepository.save(name);
        CreateCommand createCommand = new CreateCommand(UUID.randomUUID(), "test name", "test email", CustomerState.INITIALIZED, Instant.now());
        //When
        Try<CreateCommand> validCommand = createCommandValidator.valid(createCommand);
        //Then
        assertTrue(validCommand.isFailure());
        assertTrue(((BindException) validCommand.getCause()).getAllErrors().contains(nameError));
        assertEquals(1, ((BindException) validCommand.getCause()).getFieldErrors().size());
    }

    @Test
    @DisplayName("should return error (letters size) - name exists in database")
    public void shouldReturnErrorIgnoreCaseNameExistsInDatabase() {
        //Given
        FieldError nameError = new FieldError("CreateCommand", "name", "name exists in database");
        Name name = new Name("TEST name");
        nameRepository.save(name);
        CreateCommand createCommand = new CreateCommand(UUID.randomUUID(), "test name", "test email", CustomerState.INITIALIZED, Instant.now());
        //When
        Try<CreateCommand> validCommand = createCommandValidator.valid(createCommand);
        //Then
        assertTrue(validCommand.isFailure());
        assertTrue(((BindException) validCommand.getCause()).getAllErrors().contains(nameError));
        assertEquals(1, ((BindException) validCommand.getCause()).getFieldErrors().size());
    }


    @Test
    @DisplayName("should return error - email exists in database")
    public void shouldReturnErrorEmailExistsInDatabase() {
        //Given
        FieldError emailError = new FieldError("CreateCommand", "email", "email exists in database");
        Email email = new Email("test email");
        emailRepository.save(email);
        CreateCommand createCommand = new CreateCommand(UUID.randomUUID(), "test name", "test email", CustomerState.INITIALIZED, Instant.now());
        //When
        Try<CreateCommand> valid = createCommandValidator.valid(createCommand);
        //Then
        assertTrue(valid.isFailure());
        assertEquals(1, ((BindException) valid.getCause()).getFieldErrors().size());
        assertTrue(((BindException) valid.getCause()).getAllErrors().contains(emailError));
    }

    @Test
    @DisplayName("should return error (letters size) - email exists in database ")
    public void shouldReturnErrorLettersSizeEmailExistsInDatabase() {
        //Given
        FieldError emailError = new FieldError("CreateCommand", "email", "email exists in database");
        Email email = new Email("TEST@email.com");
        emailRepository.save(email);
        CreateCommand createCommand = new CreateCommand(UUID.randomUUID(), "test name", "test@email.com", CustomerState.INITIALIZED, Instant.now());
        //When
        Try<CreateCommand> valid = createCommandValidator.valid(createCommand);
        //Then
        assertTrue(valid.isFailure());
        assertEquals(1, ((BindException) valid.getCause()).getFieldErrors().size());
        assertTrue(((BindException) valid.getCause()).getAllErrors().contains(emailError));
    }

    @Test
    @DisplayName("should return two errors - email and name exist in database")
    public void shouldReturnTwoErrorsEmailAndNameExistInDatabase() {
        //Given
        FieldError emailError = new FieldError("CreateCommand", "email", "email exists in database");
        FieldError nameError = new FieldError("CreateCommand", "name", "name exists in database");
        Name name = new Name("test name");
        Email email = new Email("test email");
        nameRepository.save(name);
        emailRepository.save(email);
        CreateCommand createCommand = new CreateCommand(UUID.randomUUID(), "test name", "test email", CustomerState.INITIALIZED, Instant.now());
        //When
        Try<CreateCommand> valid = createCommandValidator.valid(createCommand);
        //Then
        assertTrue(valid.isFailure());
        assertEquals(2, ((BindException) valid.getCause()).getFieldErrors().size());
        assertTrue(((BindException) valid.getCause()).getAllErrors().contains(nameError));
        assertTrue(((BindException) valid.getCause()).getAllErrors().contains(emailError));
    }

    @Test
    @DisplayName("should return success - email and name do not exist in database")
    public void shouldReturnSuccessEmailAndNameDoNotExistInDatabase() {
        //Given
        CreateCommand createCommand = new CreateCommand(UUID.randomUUID(), "test name", "test email", CustomerState.INITIALIZED, Instant.now());
        //When
        Try<CreateCommand> valid = createCommandValidator.valid(createCommand);
        //Then
        assertTrue(valid.isSuccess());
    }
}