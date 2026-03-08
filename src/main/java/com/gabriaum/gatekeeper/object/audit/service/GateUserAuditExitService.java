package com.gabriaum.gatekeeper.object.audit.service;

import com.gabriaum.gatekeeper.infra.exception.BusinessException;
import com.gabriaum.gatekeeper.object.audit.GateUserAudit;
import com.gabriaum.gatekeeper.object.audit.repository.GateUserAuditRepository;
import com.gabriaum.gatekeeper.object.user.GateUser;
import com.gabriaum.gatekeeper.object.user.repository.GateUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class GateUserAuditExitService {
    @Autowired
    private GateUserAuditRepository auditRepository;

    @Autowired
    private GateUserRepository userRepository;

    public void registerExit(GateUser user) {
        GateUserAudit audit = auditRepository
                .findTopByGateUserAndExitInIsNullOrderByEntranceInDesc(user)
                .orElseThrow(() ->
                        new BusinessException(
                                "Nenhuma entrada aberta encontrada para registrar saída.",
                                HttpStatus.BAD_REQUEST,
                                "OPEN_ENTRANCE_NOT_FOUND"
                        )
                );

        audit.setExitIn(Instant.now());
        auditRepository.save(audit);
    }

    public void registerExitByCpf(String cpf) {
        GateUser user = userRepository.findByCpf(cpf)
                .orElseThrow(() ->
                        new BusinessException(
                                "Usuário não encontrado.",
                                HttpStatus.NOT_FOUND,
                                "USER_NOT_FOUND"
                        )
                );

        GateUserAudit audit = auditRepository
                .findTopByGateUserAndExitInIsNullOrderByEntranceInDesc(user)
                .orElseThrow(() ->
                        new BusinessException(
                                "O usuário não possui entrada aberta.",
                                HttpStatus.BAD_REQUEST,
                                "OPEN_ENTRANCE_NOT_FOUND"
                        )
                );

        audit.setExitIn(Instant.now());
        auditRepository.save(audit);
    }
}