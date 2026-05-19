package com.gabriaum.gatekeeper.auth.dto;

public record RegisterRequestDTO(
        String cpf,
        String email,
        String password
) {}