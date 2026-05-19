package com.gabriaum.gatekeeper.auth.repository;

import com.gabriaum.gatekeeper.auth.AuthenticationRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthenticationRequestRepository extends JpaRepository<AuthenticationRequest, Long> {
}