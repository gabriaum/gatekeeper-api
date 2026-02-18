package com.gabriaum.gatekeeper.object.auth.dto;

import jakarta.validation.constraints.NotNull;

public record AuthenticationLoginRequestDTO(
        @NotNull String cpf,
        @NotNull String email,
        @NotNull String password
) {}