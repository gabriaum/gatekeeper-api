package com.gabriaum.gatekeeper.object.user.service;

import com.gabriaum.gatekeeper.object.auth.AuthenticationRequest;
import com.gabriaum.gatekeeper.object.auth.repository.AuthenticationRequestRepository;
import com.gabriaum.gatekeeper.object.user.factory.GateUserFactory;
import com.gabriaum.gatekeeper.object.user.repository.GateUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GateUserApproveRequestService {
    @Autowired
    private AuthenticationRequestRepository requestRepository;

    @Autowired
    private GateUserRepository repository;

    @Autowired
    private GateUserFactory factory;

    public void approveRequest(Long id) {
        AuthenticationRequest authenticationRequest = requestRepository.findById(id).orElseThrow();
        repository.save(factory.createByRequest(authenticationRequest));
        requestRepository.deleteById(id);
    }
}