package com.gabriaum.gatekeeper.infra.security.service;

import com.gabriaum.gatekeeper.user.repository.GateUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final GateUserRepository repository;

    @Override
    public UserDetails loadUserByUsername(String cpf) throws UsernameNotFoundException {
        return repository
                .findByCpf(cpf)
                .orElseThrow(() ->
                        new UsernameNotFoundException("User not found"));
    }
}