package com.gabriaum.gatekeeper.object.user.service;

import com.gabriaum.gatekeeper.object.auth.repository.AuthenticationRequestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GateUserFailRequestService {
    private final AuthenticationRequestRepository requestRepository;

    public void failRequest(Long id) {
        requestRepository.deleteById(id);
    }
}