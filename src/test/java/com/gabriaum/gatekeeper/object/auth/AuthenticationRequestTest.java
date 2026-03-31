package com.gabriaum.gatekeeper.object.auth;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.Instant;

@SpringBootTest(classes = AuthenticationRequest.class)
class AuthenticationRequestTest {
    private AuthenticationRequest authenticationRequest;

    @BeforeEach
    public void setUp() {
        authenticationRequest = new AuthenticationRequest();
    }

    @Test
    public void testGettersAndSetters() {
        String cpf = "12345678900";
        String email = "test@gabriaum.com";
        String password = "testPassword";
        Instant requestTime = Instant.now();

        authenticationRequest.setCpf(cpf);
        authenticationRequest.setEmail(email);
        authenticationRequest.setPassword(password);
        authenticationRequest.setRequestedIn(requestTime);

        assert authenticationRequest.getCpf().equals(cpf);
        assert authenticationRequest.getEmail().equals(email);
        assert authenticationRequest.getPassword().equals(password);
        assert authenticationRequest.getRequestedIn().equals(requestTime);
    }
}