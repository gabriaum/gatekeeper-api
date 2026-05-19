package com.gabriaum.gatekeeper.audit.controller;

import com.gabriaum.gatekeeper.audit.dto.RegisterEntranceDTO;
import com.gabriaum.gatekeeper.audit.service.ExitService;
import com.gabriaum.gatekeeper.user.GateUser;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/audit/register/exit")
@RequiredArgsConstructor
public class RegisterExitController {
    private final ExitService auditExitService;

    @PostMapping("/self")
    public ResponseEntity<?> registerExit(
            @AuthenticationPrincipal GateUser user
    ) {
        auditExitService.registerExit(user);
        return ResponseEntity.ok("Saída registrada com sucesso.");
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/admin")
    public ResponseEntity<?> registerExitByCpf(
            @RequestBody RegisterEntranceDTO dto
    ) {
        auditExitService.registerExitByCpf(dto.targetCPF());
        return ResponseEntity.ok("Saída registrada com sucesso.");
    }
}