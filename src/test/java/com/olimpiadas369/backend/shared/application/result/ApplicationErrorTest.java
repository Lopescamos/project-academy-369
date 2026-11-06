package com.olimpiadas369.backend.shared.application.result;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class ApplicationErrorTest {

    @Test
    void validationErrorHasExpectedCode() {
        var error = ApplicationError.validationError("email", "wrong format");
        assertEquals("VALIDATION_ERROR", error.code());
        assertEquals("wrong format", error.details());
    }

    @Test
    void notFoundBuildsSnakeCaseCodeFromCamelCase() {
        var error = ApplicationError.notFound("ClaveRespuestas", "42");
        assertEquals("CLAVE_RESPUESTAS_NOT_FOUND", error.code());
        assertEquals("42", error.details());
    }

    @Test
    void notFoundHandlesHyphenatedResource() {
        var error = ApplicationError.notFound("clave-respuestas", "42");
        assertEquals("CLAVE_RESPUESTAS_NOT_FOUND", error.code());
    }

    @Test
    void conflictBuildsConflictCode() {
        var error = ApplicationError.conflict("Bloque", "already locked");
        assertEquals("BLOQUE_CONFLICT", error.code());
    }

    @Test
    void forbiddenAndUnauthorizedHaveFixedCodes() {
        assertEquals("FORBIDDEN", ApplicationError.forbidden("x", "y").code());
        assertEquals("UNAUTHORIZED", ApplicationError.unauthorized("y").code());
    }

    @Test
    void twoArgConstructorLeavesDetailsNull() {
        var error = new ApplicationError("X", "msg");
        assertNull(error.details());
    }
}
