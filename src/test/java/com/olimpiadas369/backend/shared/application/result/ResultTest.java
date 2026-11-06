package com.olimpiadas369.backend.shared.application.result;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ResultTest {

    @Test
    void successBehavesAsSuccess() {
        Result<Integer, String> r = Result.success(42);
        assertTrue(r.isSuccess());
        assertFalse(r.isFailure());
        assertEquals(42, r.getOrElse(0));
        assertEquals(42, r.toOptional().orElseThrow());
    }

    @Test
    void failureBehavesAsFailure() {
        Result<Integer, String> r = Result.failure("boom");
        assertFalse(r.isSuccess());
        assertTrue(r.isFailure());
        assertEquals(0, r.getOrElse(0));
        assertTrue(r.toOptional().isEmpty());
    }

    @Test
    void mapTransformsSuccessOnly() {
        Result<Integer, String> ok = Result.success(2);
        Result<Integer, String> ko = Result.failure("boom");
        assertEquals(4, ok.map(x -> x * 2).getOrElse(-1));
        assertTrue(ko.map(x -> x * 2).isFailure());
    }

    @Test
    void flatMapChainsSuccess() {
        Result<Integer, String> r = Result.<Integer, String>success(2)
                .flatMap(x -> Result.success(x + 1))
                .flatMap(x -> Result.success(x * 10));
        assertEquals(30, r.getOrElse(-1));
    }

    @Test
    void mapErrorTransformsFailureOnly() {
        Result<Integer, String> ok = Result.success(1);
        Result<Integer, String> ko = Result.failure("boom");
        assertTrue(ok.mapError(String::toUpperCase).isSuccess());
        assertEquals(
                "BOOM",
                ((Result.Failure<Integer, String>) ko.mapError(String::toUpperCase)).error());
    }

    @Test
    void recoverSwapsFailureForSuccess() {
        Result<Integer, String> recovered = Result.<Integer, String>failure("boom")
                .recover(err -> Result.success(99));
        assertEquals(99, recovered.getOrElse(-1));
    }
}
