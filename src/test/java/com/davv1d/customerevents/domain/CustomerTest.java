package com.davv1d.customerevents.domain;

import org.junit.Assert;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.*;

class CustomerTest {

    Customer customer = new Customer(UUID.randomUUID());

    @Test
    @DisplayName("deactivated customer cannot change name")
    public void deactivatedCustomerCannotChangeName() {
        //Given
        customer.deactivate();
        //When and Then
        assertThrows(IllegalStateException.class, () -> customer.changeNameTo("Tony"));
    }

    @Test
    @DisplayName("activated customer can change name")
    public void activatedCustomerCanChangeName() {
        //Given
        customer.activate();
        //When
        customer.changeNameTo("Frank");
        //Then
        assertEquals("Frank", customer.getName());
    }

    @Test
    @DisplayName("new customer can be activated")
    public void newCustomerCanBeActivated() {
        //Given
        customer.activate();
        //When
        boolean activate = customer.isActivate();
        //Then
        assertTrue(activate);
    }

    @Test
    @DisplayName("activated can be deactivated")
    public void activatedCanBeDeactivated() {
        //Given
        customer.activate();
        //When
        customer.deactivate();
        //Then
        assertTrue(customer.isDeactivate());
    }

    @Test
    @DisplayName("activated customer cannot be activated")
    public void activatedCustomerCannotBeActivated() {
        //Given
        customer.activate();
        //When and Then
        assertThrows(IllegalStateException.class, () -> customer.activate());
    }

    @Test
    @DisplayName("deactivated customer cannot be deactivated")
    public void deactivatedCustomerCannotBeDeactivated() {
        //Given
        customer.deactivate();
        //When and Then
        assertThrows(IllegalStateException.class, () -> customer.deactivate());
    }
}