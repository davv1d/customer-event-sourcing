package com.davv1d.customerevents.validation.configuration;

import com.davv1d.customerevents.command.ChangeNameCommand;
import com.davv1d.customerevents.command.CreateCommand;
import com.davv1d.customerevents.repository.EmailRepository;
import com.davv1d.customerevents.repository.NameRepository;
import com.davv1d.customerevents.validation.ChangeNameCommandValidator;
import com.davv1d.customerevents.validation.CreateCommandValidator;
import com.davv1d.customerevents.validation.Validator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ValidationConfiguration {

    @Bean
    public Validator<CreateCommand> createCommandValidator(NameRepository nameRepository, EmailRepository emailRepository) {
        return new CreateCommandValidator(nameRepository, emailRepository);
    }

    @Bean
    public Validator<ChangeNameCommand> changeNameCommandValidator(NameRepository nameRepository) {
        return new ChangeNameCommandValidator(nameRepository);
    }
}
