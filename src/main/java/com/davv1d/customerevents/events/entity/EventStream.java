package com.davv1d.customerevents.events.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.toList;
import static javax.persistence.FetchType.EAGER;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity(name = "event_streams")
public class EventStream {

    @Id
    @GeneratedValue
    private Long id;

    @Getter
    @Column(unique = true, nullable = false, name = "aggregate_uuid", length = 36, columnDefinition = "uuid")
    private UUID aggregateUUID;

    @Version
    @Column(nullable = false)
    private long version;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = EAGER)
    private final List<EventDescriptor> events = new ArrayList<>();

    public EventStream(UUID aggregateUUID) {
        this.aggregateUUID = aggregateUUID;
    }

    public void addEvents(List<EventDescriptor> events) {
        this.events.addAll(events);
    }

    public List<EventDescriptor> getEvents() {
        return events
                .stream()
                .sorted(comparing(EventDescriptor::getOccurredAt))
                .collect(toList());
    }

}
