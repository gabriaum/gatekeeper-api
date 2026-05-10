package com.gabriaum.gatekeeper.infra.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(classes = CryptographyService.class)
public class CryptographyServiceTest {
    private CryptographyService service;

    @BeforeEach
    public void setup() {
        service = new CryptographyService();
    }

    @Test
    public void testEncryptPassword() {
        String password = "mySecretPassword";
        String hash = service.encryptPassword(password);

        assert hash != null;
        assertTrue(service.validate(password, hash));
    }
}