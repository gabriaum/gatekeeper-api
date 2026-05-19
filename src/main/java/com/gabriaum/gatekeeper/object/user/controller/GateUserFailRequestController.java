package com.gabriaum.gatekeeper.object.user.controller;

import com.gabriaum.gatekeeper.object.user.service.GateUserFailRequestService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/gateuser/request")
@RequiredArgsConstructor
public class GateUserFailRequestController {
    private final GateUserFailRequestService service;

    @PostMapping("/{id}/fail")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> failRequest(@PathVariable Long id) {
        service.failRequest(id);
        return ResponseEntity.ok().build();
    }
}