package com.gabriaum.gatekeeper.user.repository;

import com.gabriaum.gatekeeper.user.GateUser;
import com.gabriaum.gatekeeper.user.enums.GateUserRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GateUserRepository extends JpaRepository<GateUser, Long> {
    Optional<GateUser> findByEmail(String email);
    Optional<GateUser> findByCpf(String cpf);
    Boolean existsByRole(GateUserRole role);
}