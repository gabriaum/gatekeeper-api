package com.gabriaum.gatekeeper.audit.service;

import com.gabriaum.gatekeeper.infra.exception.BusinessException;
import com.gabriaum.gatekeeper.audit.GateUserAudit;
import com.gabriaum.gatekeeper.audit.repository.GateUserAuditRepository;
import com.gabriaum.gatekeeper.user.GateUser;
import com.gabriaum.gatekeeper.user.repository.GateUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@RequiredArgsConstructor
public class ExitService {
    private final GateUserAuditRepository auditRepository;
    private final GateUserRepository userRepository;

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