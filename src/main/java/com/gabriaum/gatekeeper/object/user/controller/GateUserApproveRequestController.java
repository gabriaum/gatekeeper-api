package com.gabriaum.gatekeeper.object.user.controller;

import com.gabriaum.gatekeeper.object.user.service.GateUserApproveRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/gateuser/request")
public class GateUserApproveRequestController {
    @Autowired
    private GateUserApproveRequestService service;

    @PostMapping("/{id}/approve")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> approve(@PathVariable Long id) {
        service.approveRequest(id);
        return ResponseEntity.ok().build();
    }
}