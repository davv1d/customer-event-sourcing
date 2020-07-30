package com.davv1d.customerevents.repository;

import com.davv1d.customerevents.domain.Email;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Optional;

@Repository
@Transactional
public interface EmailRepository extends CrudRepository<Email, Long> {
    Optional<Email> findByValue(String value);
}
