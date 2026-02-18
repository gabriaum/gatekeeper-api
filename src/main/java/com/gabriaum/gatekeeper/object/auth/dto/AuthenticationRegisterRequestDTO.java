package com.gabriaum.gatekeeper.object.auth.dto;

public record AuthenticationRegisterRequestDTO(
        String cpf,
        String email,
        String password
) {}