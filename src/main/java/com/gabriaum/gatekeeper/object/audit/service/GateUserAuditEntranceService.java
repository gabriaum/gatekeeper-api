package com.gabriaum.gatekeeper.object.audit.service;

import com.gabriaum.gatekeeper.infra.exception.BusinessException;
import com.gabriaum.gatekeeper.object.audit.GateUserAudit;
import com.gabriaum.gatekeeper.object.audit.repository.GateUserAuditRepository;
import com.gabriaum.gatekeeper.object.user.GateUser;
import com.gabriaum.gatekeeper.object.user.repository.GateUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class GateUserAuditEntranceService {
    @Autowired
    private GateUserAuditRepository auditRepository;

    @Autowired
    private GateUserRepository userRepository;

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