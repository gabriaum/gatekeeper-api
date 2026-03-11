package com.gabriaum.gatekeeper.object.auth.controller;

import com.gabriaum.gatekeeper.object.auth.AuthenticationRequest;
import com.gabriaum.gatekeeper.object.auth.AuthenticationRequestMapper;
import com.gabriaum.gatekeeper.object.auth.dto.AuthenticationRegisterRequestDTO;
import com.gabriaum.gatekeeper.object.auth.repository.AuthenticationRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/authentication")
public class AuthenticationRequestController {
    @Autowired
    private AuthenticationRequestRepository requestRepository;

    @Autowired
    private AuthenticationRequestMapper requestMapper;

    @PostMapping("/register")
    public ResponseEntity<?> onRegister(
            @RequestBody AuthenticationRegisterRequestDTO requestDTO
    ) {
        AuthenticationRequest request = requestMapper.toEntityByDTO(requestDTO);
        requestRepository.save(request);

        return ResponseEntity
                .ok("A sua requisição de registro foi cadastrada com sucesso.");
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> onGetAll() {
        return ResponseEntity
                .ok(requestRepository.findAll());
    }
}