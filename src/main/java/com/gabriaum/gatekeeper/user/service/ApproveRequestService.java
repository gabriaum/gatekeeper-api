package com.gabriaum.gatekeeper.user.service;

import com.gabriaum.gatekeeper.auth.AuthenticationRequest;
import com.gabriaum.gatekeeper.auth.repository.AuthenticationRequestRepository;
import com.gabriaum.gatekeeper.infra.exception.BusinessException;
import com.gabriaum.gatekeeper.user.factory.GateUserFactory;
import com.gabriaum.gatekeeper.user.repository.GateUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ApproveRequestService {
    private final AuthenticationRequestRepository requestRepository;
    private final GateUserRepository repository;
    private final GateUserFactory factory;

    @Transactional
    public void approveRequest(Long id) {
        AuthenticationRequest request = requestRepository
                .findById(id)
                .orElseThrow(() -> new BusinessException(
                        "Requisição de cadastro não encontrada.",
                        HttpStatus.NOT_FOUND,
                        "AUTHENTICATION_REQUEST_NOT_FOUND"
                ));

        repository.save(factory.createByRequest(request));

        requestRepository.deleteById(id);
    }
}