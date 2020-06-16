package com.davv1d.customerevents.repository;

import com.davv1d.customerevents.events.entity.EventDescriptor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PendingEventFetcher extends CrudRepository<EventDescriptor, Long> {
    List<EventDescriptor> findTop100ByStatusOrderByOccurredAtAsc(EventDescriptor.Status status);

    default List<EventDescriptor> listPending() {
        return findTop100ByStatusOrderByOccurredAtAsc(EventDescriptor.Status.PENDING);
    }
}
