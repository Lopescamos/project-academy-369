package com.olimpiadas369.backend.shared.interfaces.rest.transform;

import com.olimpiadas369.backend.shared.application.result.ApplicationError;
import com.olimpiadas369.backend.shared.interfaces.rest.resources.ErrorResource;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 * Converts application errors to HTTP responses.
 *
 * <p>The {@code message} is localized from {@code messages*.properties} using the request locale.
 * Lookup order: a specific key for the exact error code (e.g. {@code error.bloque-not-found.message}),
 * then a generic key for the error family (e.g. {@code error.not-found.message}), then the
 * original English message. Message arguments: {@code {0}} = details, {@code {1}} = entity name
 * derived from the code.</p>
 */
@NullMarked
public final class ErrorResponseAssembler {
    private static final String MESSAGES_BASENAME = "messages";

    private static final ResourceBundle.Control NO_FALLBACK_CONTROL =
            ResourceBundle.Control.getNoFallbackControl(ResourceBundle.Control.FORMAT_PROPERTIES);

    private ErrorResponseAssembler() {
    }

    public static ResponseEntity<ErrorResource> toErrorResponseFromApplicationError(ApplicationError error) {
        HttpStatusCode status = toStatusFromErrorCode(error.code());
        String localizedMessage = toLocalizedMessageFromApplicationError(error);
        ErrorResource resource = new ErrorResource(error.code(), localizedMessage, error.details());
        return new ResponseEntity<>(resource, status);
    }

    public static HttpStatusCode toStatusFromErrorCode(String errorCode) {
        return switch (errorCode) {
            case "VALIDATION_ERROR" -> HttpStatus.BAD_REQUEST;
            case "UNAUTHORIZED" -> HttpStatus.UNAUTHORIZED;
            case "FORBIDDEN" -> HttpStatus.FORBIDDEN;
            case String s when s.endsWith("_NOT_FOUND") -> HttpStatus.NOT_FOUND;
            case "BUSINESS_RULE_VIOLATION" -> HttpStatus.UNPROCESSABLE_CONTENT;
            case String s when s.endsWith("_CONFLICT") -> HttpStatus.CONFLICT;
            default -> HttpStatus.INTERNAL_SERVER_ERROR;
        };
    }

    private static String toLocalizedMessageFromApplicationError(ApplicationError error) {
        Object[] args = {error.details(), toEntityNameFromErrorCode(error.code())};

        String specificMessage = toLocalizedMessageOrNull(toSpecificMessageKeyFromErrorCode(error.code()), args);
        if (specificMessage != null) {
            return specificMessage;
        }

        String genericMessage = toLocalizedMessageOrNull(toMessageKeyFromErrorCode(error.code()), args);
        return genericMessage != null ? genericMessage : error.message();
    }

    private static String toSpecificMessageKeyFromErrorCode(String errorCode) {
        return "error.%s.message".formatted(errorCode.toLowerCase(Locale.ROOT).replace('_', '-'));
    }

    private static String toMessageKeyFromErrorCode(String errorCode) {
        return switch (errorCode) {
            case "VALIDATION_ERROR" -> "error.validation.message";
            case "UNAUTHORIZED" -> "error.unauthorized.message";
            case "FORBIDDEN" -> "error.forbidden.message";
            case "BUSINESS_RULE_VIOLATION" -> "error.business-rule.message";
            case "UNEXPECTED_ERROR" -> "error.unexpected.message";
            case String s when s.endsWith("_NOT_FOUND") -> "error.not-found.message";
            case String s when s.endsWith("_CONFLICT") -> "error.conflict.message";
            default -> "error.generic.message";
        };
    }

    private static String toEntityNameFromErrorCode(String errorCode) {
        String entityCode;
        if (errorCode.endsWith("_NOT_FOUND")) {
            entityCode = errorCode.substring(0, errorCode.length() - "_NOT_FOUND".length());
        } else if (errorCode.endsWith("_CONFLICT")) {
            entityCode = errorCode.substring(0, errorCode.length() - "_CONFLICT".length());
        } else {
            return "resource";
        }
        return entityCode.toLowerCase(Locale.ROOT).replace('_', ' ');
    }

    private static @Nullable String toLocalizedMessageOrNull(String key, @Nullable Object... args) {
        try {
            ResourceBundle bundle = ResourceBundle.getBundle(
                    MESSAGES_BASENAME, LocaleContextHolder.getLocale(), NO_FALLBACK_CONTROL);
            if (!bundle.containsKey(key)) {
                return null;
            }
            return MessageFormat.format(bundle.getString(key), args);
        } catch (MissingResourceException ex) {
            return null;
        }
    }
}
