package com.gabriaum.gatekeeper.user.controller;

import com.gabriaum.gatekeeper.user.service.FailRequestService;
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
public class FailRequestController {
    private final FailRequestService service;

    @PostMapping("/{id}/fail")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> failRequest(@PathVariable Long id) {
        service.failRequest(id);
        return ResponseEntity.ok().build();
    }
}