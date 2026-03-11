package com.gabriaum.gatekeeper.object.auth.factory;

import com.gabriaum.gatekeeper.object.auth.AuthenticationRequest;
import com.gabriaum.gatekeeper.object.auth.dto.AuthenticationRequestResponseDTO;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationRequestResponseFactory {
    public AuthenticationRequestResponseDTO createByRequestId(AuthenticationRequest request) {
        return new AuthenticationRequestResponseDTO(
                request.getId(),
                request.getCpf(),
                request.getEmail(),
                request.getRequestedIn()
        );
    }
}
