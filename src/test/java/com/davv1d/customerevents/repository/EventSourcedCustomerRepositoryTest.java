package com.davv1d.customerevents.repository;

import com.davv1d.customerevents.domain.Customer;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class EventSourcedCustomerRepositoryTest {
    EventSourcedCustomerRepository customerRepository = new EventSourcedCustomerRepository();

    @Test
    @DisplayName("should be able to save and load customer")
    public void shouldBeAbleToSaveAndLoadCustomer() {
        //Given
        UUID uuid = UUID.randomUUID();
        Customer customer = new Customer(uuid);
        customer.activate();
        customer.changeNameTo("Barry");
        //When
        customerRepository.save(customer);
        Customer loaded = customerRepository.findBy(uuid);
        //Then
        assertTrue(loaded.isActivate());
        assertEquals("Barry", loaded.getName());
    }


    @Test
    @DisplayName("should be able to load state from a historic timestamp")
    public void shouldBeAbleToLoadStateFromAHistoricTimestamp() throws InterruptedException {
        //Given
        UUID uuid = UUID.randomUUID();
        Customer customer = new Customer(uuid);
        customer.activate();
        customer.changeNameTo("Barry");
        customerRepository.save(customer);
        Thread.sleep(2000L);
        //When
        customer.changeNameTo("Tony");
        customerRepository.save(customer);
        Customer loaded = customerRepository.find(uuid, Instant.now().minusMillis(1000));
        //Then
        assertTrue(loaded.isActivate());
        assertEquals("Barry", loaded.getName());
    }
}