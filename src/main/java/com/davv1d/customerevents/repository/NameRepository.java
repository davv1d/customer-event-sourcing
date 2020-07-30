package com.davv1d.customerevents.repository;

import com.davv1d.customerevents.domain.Name;
import javaslang.control.Try;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Optional;

@Repository
@Transactional
public interface NameRepository extends CrudRepository<Name, Long> {
    Optional<Name> findByValue(String value);

    default Try<Name> saveName(Name name) {
        if (!findByValue(name.getValue()).isPresent()) {
            return Try.success(save(name));
        } else {
            return Try.failure(new IllegalStateException("Name is exist"));
        }
    }
}
