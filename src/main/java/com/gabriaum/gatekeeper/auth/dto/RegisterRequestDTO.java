package com.gabriaum.gatekeeper.auth.dto;

import jakarta.validation.constraints.NotBlank;

public record RegisterRequestDTO(
        @NotBlank String cpf,
        @NotBlank String email,
        @NotBlank String password
) {}