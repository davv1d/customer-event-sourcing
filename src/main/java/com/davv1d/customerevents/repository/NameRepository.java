package com.davv1d.customerevents.repository;

import com.davv1d.customerevents.domain.Name;
import javaslang.control.Try;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface NameRepository extends CrudRepository<Name, Long> {
    Optional<Name> findByName(String name);

    @Query(value = "select case when count(n) > 0 then true else false end from Name n where upper(n.name) like upper(:name)")
    boolean existsByNameIgnoreCase(String name);

    @Override
    List<Name> findAll();

    default Try<Name> saveName(Name name) {
        if (!findByName(name.getName()).isPresent()) {
            return Try.success(save(name));
        } else {
            return Try.failure(new IllegalStateException("Name is exist"));
        }
    }
}
