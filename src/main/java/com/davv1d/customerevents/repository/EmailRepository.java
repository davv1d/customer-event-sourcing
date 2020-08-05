package com.davv1d.customerevents.repository;

import com.davv1d.customerevents.domain.Email;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface EmailRepository extends CrudRepository<Email, Long> {
    Optional<Email> findByEmail(String email);

    @Query(value = "select case when count(e) > 0 then true else false end from Email e where upper(e.email) like upper(:email)")
    boolean existsByEmailIgnoreCase(String email);

    @Override
    List<Email> findAll();

}
