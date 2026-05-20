package com.gabriaum.gatekeeper.infra.exception;

import java.time.Instant;
import java.util.List;

public record ApiErrorResponse(
        Instant timestamp,
        int status,
        String error,
        String message,
        String path,
        String errorCode,
        List<FieldViolation> violations
) {
    public static ApiErrorResponse of(
            int status,
            String error,
            String message,
            String path,
            String errorCode,
            List<FieldViolation> violations
    ) {
        return new ApiErrorResponse(Instant.now(), status, error, message, path, errorCode, violations);
    }

    public record FieldViolation(String field, String message) {}
}
