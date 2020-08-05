package com.davv1d.customerevents.validation;

import com.davv1d.customerevents.command.ChangeNameCommand;
import com.davv1d.customerevents.command.CreateCommand;
import com.davv1d.customerevents.domain.CustomerState;
import com.davv1d.customerevents.repository.EmailRepository;
import com.davv1d.customerevents.repository.EventStreamRepository;
import com.davv1d.customerevents.repository.NameRepository;
import com.davv1d.customerevents.service.CustomerService;
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

    @Autowired
    private EmailRepository emailRepository;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private EventStreamRepository eventStreamRepository;

    @AfterEach
    public void cleanUp() {
        eventStreamRepository.deleteAll();
        nameRepository.deleteAll();
        emailRepository.deleteAll();
    }

    @Test
    @DisplayName("Should return error - name exists in db")
    public void shouldReturnErrorNameExistsInDb() {
        //Given
        FieldError nameError = new FieldError("ChangeNameCommand", "name", "name exists in database");
        UUID uuid = UUID.randomUUID();
        CreateCommand createCommand = new CreateCommand(uuid, "test name", "test email", CustomerState.INITIALIZED, Instant.now());
        customerService.create(createCommand);
        ChangeNameCommand changeNameCommand = new ChangeNameCommand(uuid, "test name", Instant.now());
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
        UUID uuid = UUID.randomUUID();
        CreateCommand createCommand = new CreateCommand(uuid, "test name", "test email", CustomerState.INITIALIZED, Instant.now());
        customerService.create(createCommand);
        ChangeNameCommand changeNameCommand = new ChangeNameCommand(uuid, "TEST name", Instant.now());
        //When
        Try<ChangeNameCommand> valid = changeNameCommandValidator.valid(changeNameCommand);
        //Then
        assertTrue(valid.isFailure());
        assertTrue(((BindException) valid.getCause()).getAllErrors().contains(nameError));
        assertEquals(1, ((BindException) valid.getCause()).getFieldErrors().size());
    }

    @Test
    @DisplayName("Should return error - uuid does not exist in db")
    public void shouldReturnErrorUuidDoesNotExistInDb() {
        //Given
        FieldError uuidError = new FieldError("ChangeNameCommand", "uuid", "uuid does not exist in database");
        CreateCommand createCommand = new CreateCommand(UUID.randomUUID(), "test name", "test email", CustomerState.INITIALIZED, Instant.now());
        customerService.create(createCommand);
        ChangeNameCommand changeNameCommand = new ChangeNameCommand(UUID.randomUUID(), "test name 2", Instant.now());
        //When
        Try<ChangeNameCommand> valid = changeNameCommandValidator.valid(changeNameCommand);
        //Then
        assertTrue(valid.isFailure());
        assertTrue(((BindException) valid.getCause()).getAllErrors().contains(uuidError));
        assertEquals(1, ((BindException) valid.getCause()).getFieldErrors().size());
    }

    @Test
    @DisplayName("Should return two errors - uuid does not exist in db and name exists in db")
    public void shouldReturnErrorUuidDoesNotExistInDbAndNameExistsInDb() {
        //Given
        FieldError uuidError = new FieldError("ChangeNameCommand", "uuid", "uuid does not exist in database");
        FieldError nameError = new FieldError("ChangeNameCommand", "name", "name exists in database");
        CreateCommand createCommand = new CreateCommand(UUID.randomUUID(), "test name", "test email", CustomerState.INITIALIZED, Instant.now());
        customerService.create(createCommand);
        ChangeNameCommand changeNameCommand = new ChangeNameCommand(UUID.randomUUID(), "test name", Instant.now());
        //When
        Try<ChangeNameCommand> valid = changeNameCommandValidator.valid(changeNameCommand);
        //Then
        assertTrue(valid.isFailure());
        assertTrue(((BindException) valid.getCause()).getAllErrors().contains(uuidError));
        assertTrue(((BindException) valid.getCause()).getAllErrors().contains(nameError));
        assertEquals(2, ((BindException) valid.getCause()).getFieldErrors().size());
    }

    @Test
    @DisplayName("Should return success")
    public void shouldReturnErrorSuccess() {
        //Given
        UUID uuid = UUID.randomUUID();
        CreateCommand createCommand = new CreateCommand(uuid, "test name", "test email", CustomerState.INITIALIZED, Instant.now());
        customerService.create(createCommand);
        ChangeNameCommand changeNameCommand = new ChangeNameCommand(uuid, "test name 2", Instant.now());
        //When
        Try<ChangeNameCommand> valid = changeNameCommandValidator.valid(changeNameCommand);
        //Then
        assertTrue(valid.isSuccess());
    }
}
