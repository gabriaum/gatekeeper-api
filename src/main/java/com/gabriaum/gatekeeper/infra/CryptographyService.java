package com.gabriaum.gatekeeper.infra;

import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

@Service
public class CryptographyService {
    public String encryptPassword(String password) {
        String salt = BCrypt.gensalt();
        return BCrypt.hashpw(
                password,
                salt
        );
    }

    public Boolean validate(
            String password,
            String passwordHash
    ) {
        return BCrypt.checkpw(
                password,
                passwordHash
        );
    }
}
