package com.gabriaum.gatekeeper.auth.controller;

import com.gabriaum.gatekeeper.auth.AuthenticationRequest;
import com.gabriaum.gatekeeper.auth.AuthenticationRequestMapper;
import com.gabriaum.gatekeeper.auth.dto.RegisterRequestDTO;
import com.gabriaum.gatekeeper.auth.factory.ResponseFactory;
import com.gabriaum.gatekeeper.auth.repository.AuthenticationRequestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/authentication")
@RequiredArgsConstructor
public class RequestController {
    private final AuthenticationRequestRepository requestRepository;
    private final AuthenticationRequestMapper requestMapper;
    private final ResponseFactory requestResponseFactory;

    @PostMapping("/register")
    public ResponseEntity<?> onRegister(
            @RequestBody RegisterRequestDTO requestDTO
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
                .ok(requestRepository.findAll()
                        .stream()
                        .map(requestResponseFactory::createByRequestId)
                        .toList());
    }
}