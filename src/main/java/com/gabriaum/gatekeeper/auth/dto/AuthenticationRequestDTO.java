package com.gabriaum.gatekeeper.auth.dto;

import jakarta.validation.constraints.NotNull;

import java.time.Instant;

public record AuthenticationRequestDTO(
        @NotNull Long id,
        @NotNull String cpf,
        @NotNull String email,
        @NotNull Instant requestedIn
) {}