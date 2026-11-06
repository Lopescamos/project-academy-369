package com.olimpiadas369.backend.shared.application.result;

import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import java.util.Locale;

/**
 * Error produced by the application layer. Designed to be mapped to HTTP responses
 * via {@code ErrorResponseAssembler}.
 *
 * @param code    machine-readable error code (e.g. "BLOQUE_NOT_FOUND", "VALIDATION_ERROR")
 * @param message human-readable fallback (used when no i18n key is defined)
 * @param details optional additional context
 */
@NullMarked
public record ApplicationError(
        String code,
        String message,
        @Nullable String details) {

    public ApplicationError(String code, String message) {
        this(code, message, null);
    }

    public static ApplicationError validationError(String fieldOrConcept, String reason) {
        return new ApplicationError(
                "VALIDATION_ERROR",
                "Validation failed: %s".formatted(fieldOrConcept),
                reason);
    }

    public static ApplicationError notFound(String resourceType, String identifier) {
        return new ApplicationError(
                "%s_NOT_FOUND".formatted(toCodeSegment(resourceType)),
                "%s not found: %s".formatted(resourceType, identifier),
                identifier);
    }

    public static ApplicationError businessRuleViolation(String rule, String reason) {
        return new ApplicationError(
                "BUSINESS_RULE_VIOLATION",
                "Business rule violation: %s".formatted(rule),
                reason);
    }

    public static ApplicationError conflict(String resource, String reason) {
        return new ApplicationError(
                "%s_CONFLICT".formatted(toCodeSegment(resource)),
                "Conflict with %s".formatted(resource),
                reason);
    }

    public static ApplicationError forbidden(String resource, String reason) {
        return new ApplicationError(
                "FORBIDDEN",
                "Access denied to %s".formatted(resource),
                reason);
    }

    public static ApplicationError unauthorized(String reason) {
        return new ApplicationError(
                "UNAUTHORIZED",
                "Authentication required",
                reason);
    }

    public static ApplicationError unexpected(String context, String reason) {
        return new ApplicationError(
                "UNEXPECTED_ERROR",
                "Unexpected error in %s".formatted(context),
                reason);
    }

    private static String toCodeSegment(String resource) {
        return resource
                .replaceAll("([a-z0-9])([A-Z])", "$1_$2")
                .replaceAll("[\\s-]+", "_")
                .toUpperCase(Locale.ROOT);
    }
}
