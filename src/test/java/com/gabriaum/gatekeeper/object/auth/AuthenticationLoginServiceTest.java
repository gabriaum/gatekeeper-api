package com.gabriaum.gatekeeper.object.auth;

import com.gabriaum.gatekeeper.infra.service.CryptographyService;
import com.gabriaum.gatekeeper.object.auth.dto.AuthenticationLoginRequestDTO;
import com.gabriaum.gatekeeper.object.auth.service.AuthenticationLoginService;
import com.gabriaum.gatekeeper.object.user.GateUser;
import com.gabriaum.gatekeeper.object.user.repository.GateUserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthenticationLoginServiceTest {
    @Mock
    private GateUserRepository gateUserRepository;

    @Mock
    private CryptographyService cryptographyService;

    @InjectMocks
    private AuthenticationLoginService service;

    @Test
    void shouldReturnUserWhenCpfAndPasswordAreValid() {
        AuthenticationLoginRequestDTO dto =
                new AuthenticationLoginRequestDTO("123", null, "senha");

        GateUser user = new GateUser();
        user.setPassword("hashed");

        when(gateUserRepository.findByCpf("123"))
                .thenReturn(Optional.of(user));

        when(cryptographyService.validate("senha", "hashed"))
                .thenReturn(true);

        GateUser result = service.validate(dto);

        assertNotNull(result);
        assertEquals(user, result);
    }

    @Test
    void shouldReturnUserWhenEmailAndPasswordAreValid() {
        AuthenticationLoginRequestDTO dto =
                new AuthenticationLoginRequestDTO(null, "email@test.com", "senha");

        GateUser user = new GateUser();
        user.setPassword("hashed");

        when(gateUserRepository.findByEmail("email@test.com"))
                .thenReturn(Optional.of(user));

        when(cryptographyService.validate("senha", "hashed"))
                .thenReturn(true);

        GateUser result = service.validate(dto);

        assertNotNull(result);
    }

    @Test
    void shouldReturnNullWhenUserNotFound() {
        AuthenticationLoginRequestDTO dto =
                new AuthenticationLoginRequestDTO("123", null, "senha");

        when(gateUserRepository.findByCpf("123"))
                .thenReturn(Optional.empty());

        GateUser result = service.validate(dto);

        assertNull(result);
    }

    @Test
    void shouldReturnNullWhenPasswordIsInvalid() {
        AuthenticationLoginRequestDTO dto =
                new AuthenticationLoginRequestDTO("123", null, "senha");

        GateUser user = new GateUser();
        user.setPassword("hashed");

        when(gateUserRepository.findByCpf("123"))
                .thenReturn(Optional.of(user));

        when(cryptographyService.validate("senha", "hashed"))
                .thenReturn(false);

        GateUser result = service.validate(dto);

        assertNull(result);
    }
}