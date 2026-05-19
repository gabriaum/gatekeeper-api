package com.gabriaum.gatekeeper.auth.dto;

import jakarta.validation.constraints.NotNull;

public record LoginRequestDTO(
        String cpf,
        String email,
        @NotNull String password
) {}