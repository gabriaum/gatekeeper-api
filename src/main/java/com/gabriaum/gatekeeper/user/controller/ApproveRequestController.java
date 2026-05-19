package com.gabriaum.gatekeeper.user.controller;

import com.gabriaum.gatekeeper.user.service.ApproveRequestService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/gateuser/request")
@RequiredArgsConstructor
public class ApproveRequestController {
    private final ApproveRequestService service;

    @PostMapping("/{id}/approve")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> approve(@PathVariable Long id) {
        service.approveRequest(id);
        return ResponseEntity.ok().build();
    }
}