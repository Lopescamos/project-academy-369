package com.olimpiadas369.backend.shared.infrastructure.persistence.jpa.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

/**
 * Activates Spring Data JPA auditing globally so that
 * {@link com.olimpiadas369.backend.shared.infrastructure.persistence.jpa.entities.AuditableAbstractPersistenceEntity}
 * can populate {@code created_at} and {@code updated_at} automatically on insert/update.
 */
@Configuration
@EnableJpaAuditing
public class JpaAuditingConfiguration {
}
