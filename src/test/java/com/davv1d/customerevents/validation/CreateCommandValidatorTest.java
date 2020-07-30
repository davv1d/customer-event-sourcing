package com.davv1d.customerevents.validation;

import com.davv1d.customerevents.command.CreateCommand;
import com.davv1d.customerevents.domain.CustomerState;
import com.davv1d.customerevents.domain.Email;
import com.davv1d.customerevents.domain.Name;
import com.davv1d.customerevents.repository.EmailRepository;
import com.davv1d.customerevents.repository.NameRepository;
import javaslang.control.Try;
import org.junit.Assert;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;

import java.time.Instant;
import java.util.UUID;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CreateCommandValidatorTest {

    @Autowired
    private Validator<CreateCommand> createCommandValidator;

    @Autowired
    private NameRepository nameRepository;

    @Autowired
    private EmailRepository emailRepository;

    @Test
    @DisplayName("should return error name exists in database")
    public void shouldReturnErrorNameExistsInDatabase() {
        //Given
        Name name1 = new Name("test name");
        nameRepository.save(name1);
        CreateCommand createCommand = new CreateCommand(UUID.randomUUID(), "test name", "test email", CustomerState.INITIALIZED, Instant.now());
        //When
        Try<CreateCommand> validCommand = createCommandValidator.valid(createCommand);
        //Then
        Assert.assertTrue(validCommand.isFailure());
        Assert.assertEquals(1, ((BindException) validCommand.getCause()).getFieldErrors().size());
        Assert.assertEquals("name", ((BindException) validCommand.getCause()).getFieldErrors().get(0).getField());
        Assert.assertEquals("name exists in database", ((BindException) validCommand.getCause()).getFieldErrors().get(0).getDefaultMessage());
    }

    @Test
    @DisplayName("should return error email exists in database")
    public void shouldReturnErrorEmailExistsInDatabase() {
        //Given
        Email email = new Email("test email");
        emailRepository.save(email);
        CreateCommand createCommand = new CreateCommand(UUID.randomUUID(), "test name", "test email", CustomerState.INITIALIZED, Instant.now());
        //When
        Try<CreateCommand> valid = createCommandValidator.valid(createCommand);
        //Then
        Assert.assertTrue(valid.isFailure());
        Assert.assertEquals(1, ((BindException) valid.getCause()).getFieldErrors().size());
        Assert.assertEquals("email", ((BindException) valid.getCause()).getFieldErrors().get(0).getField());
        Assert.assertEquals("email exists in database", ((BindException) valid.getCause()).getFieldErrors().get(0).getDefaultMessage());
    }

    @Test
    @DisplayName("should return two errors - email and name exist in database")
    public void shouldReturnTwoErrorsEmailAndNameExistInDatabase() {
        Name name1 = new Name("test name");
        Email email = new Email("test email");
        nameRepository.save(name1);
        emailRepository.save(email);
        CreateCommand createCommand = new CreateCommand(UUID.randomUUID(), "test name", "test email", CustomerState.INITIALIZED, Instant.now());
        //When
        Try<CreateCommand> valid = createCommandValidator.valid(createCommand);
        //Then
        Assert.assertTrue(valid.isFailure());
        Assert.assertEquals(2, ((BindException) valid.getCause()).getFieldErrors().size());
        Assert.assertEquals("name", ((BindException) valid.getCause()).getFieldErrors().get(0).getField());
        Assert.assertEquals("email", ((BindException) valid.getCause()).getFieldErrors().get(1).getField());
        Assert.assertEquals("name exists in database", ((BindException) valid.getCause()).getFieldErrors().get(0).getDefaultMessage());
        Assert.assertEquals("email exists in database", ((BindException) valid.getCause()).getFieldErrors().get(1).getDefaultMessage());
    }

    @Test
    @DisplayName("should return success email and name do not exist in database")
    public void shouldReturnSuccessEmailAndNameDoNotExistInDatabase() {
        //Given
        //When
        //Then
    }
}