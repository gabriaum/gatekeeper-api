package com.gabriaum.gatekeeper.object.auth.controller;

import com.gabriaum.gatekeeper.infra.security.service.TokenService;
import com.gabriaum.gatekeeper.object.auth.dto.AuthenticationLoginRequestDTO;
import com.gabriaum.gatekeeper.object.auth.service.AuthenticationLoginService;
import com.gabriaum.gatekeeper.object.user.GateUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/authentication/login")
public class AuthenticationLoginController {
    @Autowired
    private AuthenticationLoginService loginService;

    @Autowired
    private TokenService tokenService;

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