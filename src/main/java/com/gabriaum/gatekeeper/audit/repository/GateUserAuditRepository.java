package com.gabriaum.gatekeeper.audit.repository;

import com.gabriaum.gatekeeper.audit.GateUserAudit;
import com.gabriaum.gatekeeper.user.GateUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GateUserAuditRepository extends JpaRepository<GateUserAudit, Long> {
    Optional<GateUserAudit> findTopByGateUserAndExitInIsNullOrderByEntranceInDesc(GateUser gateUser);
}