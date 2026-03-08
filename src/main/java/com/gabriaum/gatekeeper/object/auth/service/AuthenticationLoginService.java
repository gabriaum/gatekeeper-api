package com.gabriaum.gatekeeper.object.auth.service;

import com.gabriaum.gatekeeper.infra.security.service.TokenService;
import com.gabriaum.gatekeeper.infra.service.CryptographyService;
import com.gabriaum.gatekeeper.object.auth.dto.AuthenticationLoginRequestDTO;
import com.gabriaum.gatekeeper.object.user.GateUser;
import com.gabriaum.gatekeeper.object.user.repository.GateUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthenticationLoginService {
    @Autowired
    private GateUserRepository gateUserRepository;

    @Autowired
    private CryptographyService cryptographyService;

    public GateUser validate(AuthenticationLoginRequestDTO dto) {
        String cpf = dto.cpf();
        String email = dto.email();
        String password = dto.password();

        GateUser gateUser = null;
        if (cpf != null && !cpf.isBlank()) {
            Optional<GateUser> byCpf = gateUserRepository.findByCpf(cpf);
            if (byCpf.isPresent())
                gateUser = byCpf.get();
        }

        if (gateUser == null && email != null && !email.isBlank()) {
            Optional<GateUser> byEmail = gateUserRepository.findByEmail(email);
            if (byEmail.isPresent())
                gateUser = byEmail.get();
        }

        return gateUser != null &&
                cryptographyService.validate(
                        password,
                        gateUser.getPassword()
                ) ? gateUser : null;
    }
}