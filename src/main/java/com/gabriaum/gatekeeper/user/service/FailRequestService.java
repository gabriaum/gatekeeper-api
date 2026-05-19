package com.gabriaum.gatekeeper.user.service;

import com.gabriaum.gatekeeper.auth.repository.AuthenticationRequestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FailRequestService {
    private final AuthenticationRequestRepository requestRepository;

    public void failRequest(Long id) {
        requestRepository.deleteById(id);
    }
}