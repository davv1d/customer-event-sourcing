package com.davv1d.customerevents.repository;

import com.davv1d.customerevents.domain.Customer;
import javaslang.control.Try;

import java.util.UUID;

public interface CustomerRepository {
    UUID save(Customer customer);

    Try<Customer> getByUUID(UUID uuid);
}
