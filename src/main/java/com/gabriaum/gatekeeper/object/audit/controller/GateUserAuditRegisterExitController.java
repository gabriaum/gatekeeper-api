package com.gabriaum.gatekeeper.object.audit.controller;

import com.gabriaum.gatekeeper.object.audit.dto.GateUserAuditRegisterEntranceDTO;
import com.gabriaum.gatekeeper.object.audit.service.GateUserAuditExitService;
import com.gabriaum.gatekeeper.object.user.GateUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/audit/register/exit")
public class GateUserAuditRegisterExitController {
    @Autowired
    private GateUserAuditExitService auditExitService;

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
            @RequestBody GateUserAuditRegisterEntranceDTO dto
    ) {
        auditExitService.registerExitByCpf(dto.targetCPF());
        return ResponseEntity.ok("Saída registrada com sucesso.");
    }
}