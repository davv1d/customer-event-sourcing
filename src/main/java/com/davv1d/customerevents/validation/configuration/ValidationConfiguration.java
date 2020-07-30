package com.davv1d.customerevents.validation.configuration;

import com.davv1d.customerevents.command.CreateCommand;
import com.davv1d.customerevents.repository.NameRepository;
import com.davv1d.customerevents.validation.CreateCommandValidator;
import com.davv1d.customerevents.validation.Validator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ValidationConfiguration {
//
//    @Autowired
//    private NameRepository nameRepository;

    @Bean
    public Validator<CreateCommand> createCommandValidator(NameRepository nameRepository) {
        return new CreateCommandValidator(nameRepository);
    }
}