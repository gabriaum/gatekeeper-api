package com.gabriaum.gatekeeper.auth.dto;

import jakarta.validation.constraints.NotBlank;

public record LoginRequestDTO(
        String cpf,
        String email,
        @NotBlank String password
) {}