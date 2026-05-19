package com.gabriaum.gatekeeper.auth.factory;

import com.gabriaum.gatekeeper.auth.AuthenticationRequest;
import com.gabriaum.gatekeeper.auth.dto.AuthenticationRequestDTO;
import org.springframework.stereotype.Component;

@Component
public class ResponseFactory {
    public AuthenticationRequestDTO createByRequestId(AuthenticationRequest request) {
        return new AuthenticationRequestDTO(
                request.getId(),
                request.getCpf(),
                request.getEmail(),
                request.getRequestedIn()
        );
    }
}
