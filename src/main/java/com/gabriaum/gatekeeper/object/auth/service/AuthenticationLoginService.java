package com.gabriaum.gatekeeper.object.auth.service;

import com.gabriaum.gatekeeper.infra.service.CryptographyService;
import com.gabriaum.gatekeeper.object.auth.dto.AuthenticationLoginRequestDTO;
import com.gabriaum.gatekeeper.object.user.GateUser;
import com.gabriaum.gatekeeper.object.user.repository.GateUserRepository;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationLoginService {
    private final GateUserRepository gateUserRepository;
    private final CryptographyService cryptographyService;

    public AuthenticationLoginService(
            GateUserRepository gateUserRepository,
            CryptographyService cryptographyService
    ) {
        this.gateUserRepository = gateUserRepository;
        this.cryptographyService = cryptographyService;
    }

    public GateUser validate(AuthenticationLoginRequestDTO dto) {
        GateUser gateUser = findUser(dto);
        if (gateUser == null)
            return null;

        boolean valid = cryptographyService.validate(
                dto.password(),
                gateUser.getPassword()
        );

        return valid ? gateUser : null;
    }

    private GateUser findUser(AuthenticationLoginRequestDTO dto) {
        if (dto.cpf() != null && !dto.cpf().isBlank())
            return gateUserRepository.findByCpf(dto.cpf()).orElse(null);

        if (dto.email() != null && !dto.email().isBlank())
            return gateUserRepository.findByEmail(dto.email()).orElse(null);

        return null;
    }
}