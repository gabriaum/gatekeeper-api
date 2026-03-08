package com.gabriaum.gatekeeper.object.auth.dto;

import jakarta.validation.constraints.NotNull;

public record AuthenticationLoginRequestDTO(
        String cpf,
        String email,
        @NotNull String password
) {}