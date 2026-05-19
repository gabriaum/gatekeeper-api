package com.gabriaum.gatekeeper.auth.service;

import com.gabriaum.gatekeeper.infra.service.CryptographyService;
import com.gabriaum.gatekeeper.auth.dto.LoginRequestDTO;
import com.gabriaum.gatekeeper.user.GateUser;
import com.gabriaum.gatekeeper.user.repository.GateUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoginService {
    private final GateUserRepository gateUserRepository;
    private final CryptographyService cryptographyService;

    public GateUser validate(LoginRequestDTO dto) {
        GateUser gateUser = findUser(dto);
        if (gateUser == null)
            return null;

        boolean valid = cryptographyService.validate(
                dto.password(),
                gateUser.getPassword()
        );

        return valid ? gateUser : null;
    }

    private GateUser findUser(LoginRequestDTO dto) {
        if (dto.cpf() != null && !dto.cpf().isBlank())
            return gateUserRepository.findByCpf(dto.cpf()).orElse(null);

        if (dto.email() != null && !dto.email().isBlank())
            return gateUserRepository.findByEmail(dto.email()).orElse(null);

        return null;
    }
}