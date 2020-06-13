package com.davv1d.customerevents.mapper;

import com.davv1d.customerevents.events.CustomerActivated;
import com.davv1d.customerevents.events.CustomerNameChanged;
import com.davv1d.customerevents.events.DomainEvent;
import com.davv1d.customerevents.events.entity.EventDescriptor;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.UUID;

class EventSerializerTest {
    EventSerializer eventSerializer = new EventSerializer();

    @Test
    public void testSerialize() {
        //Given
        CustomerNameChanged customerNameChanged = new CustomerNameChanged(UUID.randomUUID(), "test name", Instant.now());
        CustomerActivated customerActivated = new CustomerActivated(UUID.randomUUID(), Instant.now());
        //When
        EventDescriptor serialize = eventSerializer.serialize(customerNameChanged);
        EventDescriptor serialize1 = eventSerializer.serialize(customerActivated);
        //Then
        System.out.println(serialize);
        System.out.println(serialize1);
    }


    @Test
    public void testDeserialize() {
        //Given
        CustomerNameChanged customerNameChanged = new CustomerNameChanged(UUID.randomUUID(), "test name", Instant.now());
        CustomerActivated customerActivated = new CustomerActivated(UUID.randomUUID(), Instant.now());
        EventDescriptor serialize = eventSerializer.serialize(customerNameChanged);
        EventDescriptor serialize1 = eventSerializer.serialize(customerActivated);
        System.out.println(serialize);
        System.out.println(serialize1);
        //When
        DomainEvent deserialize = eventSerializer.deserialize(serialize);
        DomainEvent deserialize1 = eventSerializer.deserialize(serialize1);
        //Then
        System.out.println(deserialize);
        System.out.println(deserialize1);
    }
}