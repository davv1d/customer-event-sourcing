package com.davv1d.customerevents.repository;

import com.davv1d.customerevents.events.entity.EventDescriptor;
import com.davv1d.customerevents.events.entity.EventStream;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static java.util.Collections.emptyList;

@Repository
public interface EventStreamRepository extends CrudRepository<EventStream, Long> {

    Optional<EventStream> findByAggregateUUID(UUID uuid);

    default void saveEvents(UUID aggregateId, List<EventDescriptor> events) {
        final EventStream eventStream = findByAggregateUUID(aggregateId)
                .orElseGet(() -> new EventStream(aggregateId));
        eventStream.addEvents(events);
        save(eventStream);
    }

    default List<EventDescriptor> getEventsForAggregate(UUID aggregateId) {
        return findByAggregateUUID(aggregateId)
                .map(EventStream::getEvents)
                .orElse(emptyList());
    }
}
