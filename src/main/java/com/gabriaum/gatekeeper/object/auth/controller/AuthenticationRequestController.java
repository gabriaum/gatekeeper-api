package com.gabriaum.gatekeeper.object.auth.controller;

import com.gabriaum.gatekeeper.object.auth.AuthenticationRequest;
import com.gabriaum.gatekeeper.object.auth.AuthenticationRequestMapper;
import com.gabriaum.gatekeeper.object.auth.dto.AuthenticationLoginRequestDTO;
import com.gabriaum.gatekeeper.object.auth.repository.AuthenticationRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/authentication/register")
public class AuthenticationRequestController {
    @Autowired
    private AuthenticationRequestRepository requestRepository;

    @Autowired
    private AuthenticationRequestMapper requestMapper;

    @PostMapping()
    public ResponseEntity<?> onRegister(
            @RequestBody AuthenticationLoginRequestDTO requestDTO
    ) {
        AuthenticationRequest request = requestMapper.toEntityByDTO(requestDTO);
        requestRepository.save(request);

        return ResponseEntity
                .ok("A sua requisição de registro foi cadastrada com sucesso.");
    }
}