package com.gabriaum.gatekeeper.object.user.repository;

import com.gabriaum.gatekeeper.object.user.GateUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GateUserRepository extends JpaRepository<GateUser, Long> {}