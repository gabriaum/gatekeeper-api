package com.gabriaum.gatekeeper.object.user.service;

import com.gabriaum.gatekeeper.object.auth.repository.AuthenticationRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GateUserFailRequestService {
    @Autowired
    private AuthenticationRequestRepository requestRepository;

    public void failRequest(Long id) {
        requestRepository.deleteById(id);
    }
}