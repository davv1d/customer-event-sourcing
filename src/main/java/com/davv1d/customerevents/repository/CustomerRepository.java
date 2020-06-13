package com.davv1d.customerevents.repository;

import com.davv1d.customerevents.domain.Customer;

import java.util.UUID;

public interface CustomerRepository {
    void save(Customer customer);

    Customer findBy(UUID uuid);
}
