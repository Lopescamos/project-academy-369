package com.olimpiadas369.backend.shared.interfaces.rest;

import com.olimpiadas369.backend.shared.application.result.ApplicationError;
import com.olimpiadas369.backend.shared.interfaces.rest.resources.ErrorResource;
import com.olimpiadas369.backend.shared.interfaces.rest.transform.ErrorResponseAssembler;
import lombok.extern.slf4j.Slf4j;
import org.jspecify.annotations.NullMarked;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.stream.Stream;

/**
 * Centralized exception handler for the REST API.
 *
 * <p>Command services return {@link com.olimpiadas369.backend.shared.application.result.Result}
 * instead of throwing, so this handler only covers what happens outside them: request
 * binding/validation, value object constructors rejecting input, method security, and
 * genuine bugs.</p>
 */
@Slf4j
@NullMarked
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResource> handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
        var bindingResult = ex.getBindingResult();
        var errorDetails = Stream.concat(
                        bindingResult.getFieldErrors().stream()
                                .map(error -> "%s: %s".formatted(error.getField(), error.getDefaultMessage())),
                        bindingResult.getGlobalErrors().stream()
                                .map(error -> String.valueOf(error.getDefaultMessage())))
                .reduce((a, b) -> a + "; " + b)
                .orElse("Request validation failed");

        var applicationError = ApplicationError.validationError("request-body", errorDetails);
        return ErrorResponseAssembler.toErrorResponseFromApplicationError(applicationError);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResource> handleHttpMessageNotReadable(HttpMessageNotReadableException ex) {
        var applicationError = ApplicationError.validationError(
                "request-body", "Malformed or unreadable request body");
        return ErrorResponseAssembler.toErrorResponseFromApplicationError(applicationError);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorResource> handleMethodArgumentTypeMismatch(MethodArgumentTypeMismatchException ex) {
        var applicationError = ApplicationError.validationError(
                ex.getName(), "Invalid value '%s'".formatted(ex.getValue()));
        return ErrorResponseAssembler.toErrorResponseFromApplicationError(applicationError);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResource> handleIllegalArgumentException(IllegalArgumentException ex) {
        var applicationError = ApplicationError.validationError(
                "request-argument",
                ex.getMessage() != null ? ex.getMessage() : "Request validation failed");
        return ErrorResponseAssembler.toErrorResponseFromApplicationError(applicationError);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorResource> handleAccessDenied(AccessDeniedException ex) {
        var applicationError = ApplicationError.forbidden("resource", "Insufficient permissions");
        return ErrorResponseAssembler.toErrorResponseFromApplicationError(applicationError);
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ErrorResource> handleAuthentication(AuthenticationException ex) {
        var applicationError = ApplicationError.unauthorized(
                ex.getMessage() != null ? ex.getMessage() : "Authentication failed");
        return ErrorResponseAssembler.toErrorResponseFromApplicationError(applicationError);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResource> handleException(Exception ex) {
        if (ex instanceof ErrorResponse errorResponse) {
            return toErrorResponseFromSpringErrorResponse(errorResponse);
        }
        log.error("Unhandled exception while processing request", ex);
        var applicationError = ApplicationError.unexpected("request-processing", "An unexpected error occurred");
        return ErrorResponseAssembler.toErrorResponseFromApplicationError(applicationError);
    }

    private ResponseEntity<ErrorResource> toErrorResponseFromSpringErrorResponse(ErrorResponse errorResponse) {
        var status = errorResponse.getStatusCode();
        var detail = errorResponse.getBody().getDetail();
        if (status.value() == HttpStatus.BAD_REQUEST.value()) {
            var applicationError = ApplicationError.validationError(
                    "request", detail != null ? detail : "Request validation failed");
            return ErrorResponseAssembler.toErrorResponseFromApplicationError(applicationError);
        }
        var code = status instanceof HttpStatus httpStatus ? httpStatus.name() : "HTTP_" + status.value();
        var message = detail != null ? detail : "Request could not be processed";
        return ResponseEntity.status(status).body(new ErrorResource(code, message));
    }
}
