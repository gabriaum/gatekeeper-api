package com.gabriaum.gatekeeper.object.auth;

import com.gabriaum.gatekeeper.infra.CryptographyService;
import com.gabriaum.gatekeeper.object.auth.dto.AuthenticationLoginRequestDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
public class AuthenticationRequestMapper {
    @Autowired
    private CryptographyService cryptographyService;

    public AuthenticationRequest toEntityByDTO(
            AuthenticationLoginRequestDTO requestDTO
    ) {
        AuthenticationRequest request = new AuthenticationRequest();
        request.setCpf(requestDTO.cpf());
        request.setEmail(requestDTO.email());
        request.setPassword(cryptographyService.encryptPassword(requestDTO.password()));
        request.setRequestedIn(Instant.now());

        return request;
    }
}
