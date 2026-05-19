package com.gabriaum.gatekeeper.auth.dto;

public record AuthenticationRegisterRequestDTO(
        String cpf,
        String email,
        String password
) {}