package com.gabriaum.gatekeeper.object.auth.dto;

import jakarta.validation.constraints.NotNull;

import java.time.Instant;

public record AuthenticationRequestResponseDTO(
        @NotNull Long id,
        @NotNull String cpf,
        @NotNull String email,
        @NotNull Instant requestedIn
) {}