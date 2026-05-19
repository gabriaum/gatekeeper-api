package com.gabriaum.gatekeeper.audit.service;

import com.gabriaum.gatekeeper.infra.exception.BusinessException;
import com.gabriaum.gatekeeper.audit.GateUserAudit;
import com.gabriaum.gatekeeper.audit.repository.GateUserAuditRepository;
import com.gabriaum.gatekeeper.user.GateUser;
import com.gabriaum.gatekeeper.user.repository.GateUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@RequiredArgsConstructor
public class EntranceService {
    private final GateUserAuditRepository auditRepository;
    private final GateUserRepository userRepository;

    public void registerEntrance(GateUser user) {
        GateUserAudit audit = new GateUserAudit();
        audit.setGateUser(user);
        audit.setEntranceIn(Instant.now());
        auditRepository.save(audit);
    }

    public void registerEntranceByCpf(String cpf) {
        GateUser user = userRepository.findByCpf(cpf)
                .orElseThrow(() -> new BusinessException("Usuário não encontrado"));

        GateUserAudit audit = new GateUserAudit();
        audit.setGateUser(user);
        audit.setEntranceIn(Instant.now());
        auditRepository.save(audit);
    }
}