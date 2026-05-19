package com.gabriaum.gatekeeper.auth;

import com.gabriaum.gatekeeper.infra.service.CryptographyService;
import com.gabriaum.gatekeeper.auth.dto.AuthenticationRegisterRequestDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
@RequiredArgsConstructor
public class AuthenticationRequestMapper {
    private final CryptographyService cryptographyService;

    public AuthenticationRequest toEntityByDTO(
            AuthenticationRegisterRequestDTO requestDTO
    ) {
        AuthenticationRequest request = new AuthenticationRequest();
        request.setCpf(requestDTO.cpf());
        request.setEmail(requestDTO.email());
        request.setPassword(cryptographyService.encryptPassword(requestDTO.password()));
        request.setRequestedIn(Instant.now());

        return request;
    }
}
