package com.olimpiadas369.backend.shared.infrastructure.persistence.jpa.configuration.strategy;

import org.hibernate.boot.model.naming.Identifier;
import org.hibernate.boot.model.naming.PhysicalNamingStrategy;
import org.hibernate.engine.jdbc.env.spi.JdbcEnvironment;

import java.util.Locale;

/**
 * Physical naming strategy for Hibernate: columns in snake_case, table names in snake_case AND
 * pluralized. {@code UsuarioSedePersistenceEntity} → {@code usuario_sedes},
 * {@code nombreCompleto} → {@code nombre_completo}.
 *
 * <p>The "PersistenceEntity" suffix is stripped before camel-to-snake conversion so the
 * canonical suffix never ends up in the table name.</p>
 */
public class SnakeCaseWithPluralizedTablePhysicalNamingStrategy implements PhysicalNamingStrategy {

    private static final String ENTITY_SUFFIX = "PersistenceEntity";

    @Override public Identifier toPhysicalCatalogName(Identifier name, JdbcEnvironment ctx) { return name; }

    @Override public Identifier toPhysicalSchemaName(Identifier name, JdbcEnvironment ctx) { return name; }

    @Override
    public Identifier toPhysicalTableName(Identifier name, JdbcEnvironment ctx) {
        if (name == null) return null;
        String text = name.getText();
        if (text.endsWith(ENTITY_SUFFIX)) {
            text = text.substring(0, text.length() - ENTITY_SUFFIX.length());
        }
        return Identifier.toIdentifier(pluralize(camelToSnake(text)), name.isQuoted());
    }

    @Override
    public Identifier toPhysicalSequenceName(Identifier name, JdbcEnvironment ctx) {
        return name == null ? null : Identifier.toIdentifier(camelToSnake(name.getText()), name.isQuoted());
    }

    @Override
    public Identifier toPhysicalColumnName(Identifier name, JdbcEnvironment ctx) {
        return name == null ? null : Identifier.toIdentifier(camelToSnake(name.getText()), name.isQuoted());
    }

    private static String camelToSnake(String input) {
        return input
                .replaceAll("([a-z0-9])([A-Z])", "$1_$2")
                .replaceAll("([A-Z]+)([A-Z][a-z])", "$1_$2")
                .toLowerCase(Locale.ROOT);
    }

    private static String pluralize(String snake) {
        if (snake.isEmpty() || snake.endsWith("s")) return snake;
        if (snake.endsWith("y")) return snake.substring(0, snake.length() - 1) + "ies";
        if (snake.endsWith("z")) return snake + "es";
        return snake + "s";
    }
}
