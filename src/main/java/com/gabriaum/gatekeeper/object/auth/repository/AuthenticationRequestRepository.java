package com.gabriaum.gatekeeper.object.auth.repository;

import com.gabriaum.gatekeeper.object.auth.AuthenticationRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthenticationRequestRepository extends JpaRepository<AuthenticationRequest, Long> {
}