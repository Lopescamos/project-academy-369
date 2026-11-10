package com.olimpiadas369.backend.shared.infrastructure.events;

import com.olimpiadas369.backend.shared.domain.model.aggregates.AbstractDomainAggregateRoot;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

/**
 * Publishes the domain events registered inside an aggregate root through Spring's
 * {@link ApplicationEventPublisher}, then clears them.
 *
 * <p>Repository adapters call {@link #publishAndClear(AbstractDomainAggregateRoot)} after
 * persisting the aggregate so events only leave the aggregate once the write is durable.</p>
 */
@Component
public class SpringDomainEventPublisher {

    private final ApplicationEventPublisher publisher;

    public SpringDomainEventPublisher(ApplicationEventPublisher publisher) {
        this.publisher = publisher;
    }

    public <T extends AbstractDomainAggregateRoot<T>> void publishAndClear(T aggregate) {
        aggregate.domainEvents().forEach(publisher::publishEvent);
        aggregate.clearDomainEvents();
    }
}
