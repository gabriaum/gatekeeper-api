package com.gabriaum.gatekeeper.object.user.service;

import com.gabriaum.gatekeeper.object.auth.AuthenticationRequest;
import com.gabriaum.gatekeeper.object.auth.repository.AuthenticationRequestRepository;
import com.gabriaum.gatekeeper.object.user.factory.GateUserFactory;
import com.gabriaum.gatekeeper.object.user.repository.GateUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class GateUserApproveRequestService {
    private final AuthenticationRequestRepository requestRepository;
    private final GateUserRepository repository;
    private final GateUserFactory factory;

    @Transactional
    public void approveRequest(Long id) {
        AuthenticationRequest request = requestRepository
                .findById(id)
                .orElseThrow();

        repository.save(factory.createByRequest(request));

        requestRepository.deleteById(id);
    }
}