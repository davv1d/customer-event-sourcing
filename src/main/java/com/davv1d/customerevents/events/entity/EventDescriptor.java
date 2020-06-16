package com.davv1d.customerevents.events.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.Instant;
import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class EventDescriptor {

    public enum Status {
        PENDING, SENT
    }

    @Id
    @GeneratedValue
    @Column(name = "ID")
    private Long id;

    @Column(name = "BODY", nullable = false, length = 600)
    private String body;

    @Column(name = "OCCURRED_AT", nullable = false)
    private Instant occurredAt;

    @Column(name = "TYPE", nullable = false, length = 60)
    private String type;

    @Column(name = "STATUS", nullable = false)
    @Enumerated(EnumType.STRING)
    private Status status = Status.PENDING;

    @Column(name = "AGGREGATE_UUID", nullable = false, length = 36, columnDefinition = "uuid")
    private UUID aggregateUUID;

    public EventDescriptor(String body, Instant occurredAt, String type, UUID aggregateUUID) {
        this.body = body;
        this.occurredAt = occurredAt;
        this.type = type;
        this.aggregateUUID = aggregateUUID;
    }

    public EventDescriptor sent() {
        this.status = Status.SENT;
        return this;
    }

    @Override
    public String toString() {
        return "EventDescriptor{" +
                "id=" + id +
                ", body='" + body + '\'' +
                ", occurredAt=" + occurredAt +
                ", type='" + type + '\'' +
                ", status=" + status +
                ", aggregateUUID=" + aggregateUUID +
                '}';
    }
}
