package com.gabriaum.gatekeeper.infra.security.service;

import com.gabriaum.gatekeeper.object.user.repository.GateUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    private GateUserRepository repository;

    @Override
    public UserDetails loadUserByUsername(String cpf) throws UsernameNotFoundException {
        return repository
                .findByCpf(cpf)
                .orElseThrow(() ->
                        new UsernameNotFoundException("User not found"));
    }
}