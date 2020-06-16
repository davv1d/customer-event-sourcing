package com.davv1d.customerevents.publisher;

import com.davv1d.customerevents.events.entity.EventDescriptor;
import com.davv1d.customerevents.repository.PendingEventFetcher;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class EventPublisher {
    private final PublishChannel publishChannel;
    private final PendingEventFetcher pendingEventFetcher;

    public EventPublisher(PublishChannel publishChannel, PendingEventFetcher pendingEventFetcher) {
        this.publishChannel = publishChannel;
        this.pendingEventFetcher = pendingEventFetcher;
    }

    @Scheduled(fixedRate = 2000)
    public void publishPending() {
        pendingEventFetcher.listPending().forEach(this::sendSafely);
    }

    private EventDescriptor sendSafely(EventDescriptor event) {
        final String body = event.getBody();
        try {
            publishChannel.sendEvent(body, event.getAggregateUUID());
            pendingEventFetcher.save(event.sent());
            log.info("send: {}", body);
        } catch (Exception e) {
            log.error("cannot send {}", body, e);
        }
        return event;
    }
}
