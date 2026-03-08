package com.gabriaum.gatekeeper.object.user.repository;

import com.gabriaum.gatekeeper.object.user.GateUser;
import com.gabriaum.gatekeeper.object.user.enums.GateUserRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GateUserRepository extends JpaRepository<GateUser, Long> {
    Optional<GateUser> findByEmail(String email);
    Optional<GateUser> findByCpf(String cpf);
    Boolean existsByRole(GateUserRole role);
}