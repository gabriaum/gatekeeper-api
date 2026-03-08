package com.gabriaum.gatekeeper.object.audit.repository;

import com.gabriaum.gatekeeper.object.audit.GateUserAudit;
import com.gabriaum.gatekeeper.object.user.GateUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GateUserAuditRepository extends JpaRepository<GateUserAudit, Long> {
    Optional<GateUserAudit> findTopByGateUserAndExitInIsNullOrderByEntranceInDesc(GateUser gateUser);
}