package com.gabriaum.gatekeeper.auth.controller;

import com.gabriaum.gatekeeper.infra.security.service.TokenService;
import com.gabriaum.gatekeeper.auth.dto.AuthenticationLoginRequestDTO;
import com.gabriaum.gatekeeper.auth.service.AuthenticationLoginService;
import com.gabriaum.gatekeeper.user.GateUser;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/authentication/login")
@RequiredArgsConstructor
public class AuthenticationLoginController {
    private final AuthenticationLoginService loginService;
    private final TokenService tokenService;

    @PostMapping
    public ResponseEntity<?> onLogin(
            @RequestBody AuthenticationLoginRequestDTO dto
    ) {
        GateUser validate = loginService.validate(dto);
        if (validate == null)
            return ResponseEntity
                    .badRequest()
                    .body("As credenciais informadas não correspondem à uma conta existente.");

        String token = tokenService.generateToken(validate);
        return ResponseEntity
                .ok(token);
    }
}