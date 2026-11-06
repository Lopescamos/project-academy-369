package com.olimpiadas369.backend.shared.domain.model.aggregates;

import org.jspecify.annotations.NullMarked;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

/**
 * Base class for all domain aggregate roots.
 *
 * <p>Provides domain event registration in plain Java, with no dependency on
 * Spring, Spring Data or JPA, so the domain layer stays framework-free.
 * Identity and auditing are intentionally left to the infrastructure layer.</p>
 *
 * <p>Repository adapters retrieve the registered events with {@link #domainEvents()},
 * publish them after persisting the aggregate, and then call {@link #clearDomainEvents()}.</p>
 *
 * @param <T> the concrete aggregate root type
 */
@NullMarked
public abstract class AbstractDomainAggregateRoot<T extends AbstractDomainAggregateRoot<T>> {

    private final List<Object> domainEvents = new ArrayList<>();

    protected void registerDomainEvent(Object event) {
        Objects.requireNonNull(event, "[AbstractDomainAggregateRoot] domain event cannot be null");
        this.domainEvents.add(event);
    }

    public Collection<Object> domainEvents() {
        return List.copyOf(this.domainEvents);
    }

    public void clearDomainEvents() {
        this.domainEvents.clear();
    }
}
