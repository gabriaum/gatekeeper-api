package com.gabriaum.gatekeeper.object.audit.controller;

import com.gabriaum.gatekeeper.object.audit.dto.GateUserAuditRegisterEntranceDTO;
import com.gabriaum.gatekeeper.object.audit.repository.GateUserAuditRepository;
import com.gabriaum.gatekeeper.object.audit.service.GateUserAuditEntranceService;
import com.gabriaum.gatekeeper.object.user.GateUser;
import com.gabriaum.gatekeeper.object.user.repository.GateUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/audit/register/entrance")
public class GateUserAuditRegisterEntranceController {
    @Autowired
    private GateUserAuditRepository auditRepository;

    @Autowired
    private GateUserRepository gateUserRepository;

    @Autowired
    private GateUserAuditEntranceService auditService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/admin")
    public ResponseEntity<?> onRegisterByAdminEntrance(
            @RequestBody GateUserAuditRegisterEntranceDTO entranceDTO
            ) {
        auditService.registerEntranceByCpf(entranceDTO.targetCPF());
        return ResponseEntity.ok("Entrada registrada com sucesso.");
    }

    @PostMapping("/self")
    public ResponseEntity<?> onRegisterSelfEntrance(
            @AuthenticationPrincipal GateUser gateUser
    ) {
        auditService.registerEntrance(gateUser);
        return ResponseEntity.ok("Entrada registrada com sucesso.");
    }
}