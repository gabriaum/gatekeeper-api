package com.gabriaum.gatekeeper.object.user.controller;

import com.gabriaum.gatekeeper.object.user.service.GateUserFailRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/gateuser/request")
public class GateUserFailRequestController {
    @Autowired
    private GateUserFailRequestService service;

    @PostMapping("/{id}/fail")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> failRequest(@PathVariable Long id) {
        service.failRequest(id);
        return ResponseEntity.ok().build();
    }
}