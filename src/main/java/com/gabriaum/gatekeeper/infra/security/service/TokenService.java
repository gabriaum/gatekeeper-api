package com.gabriaum.gatekeeper.infra.security.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.gabriaum.gatekeeper.object.user.GateUser;
import com.gabriaum.gatekeeper.object.user.repository.GateUserRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class TokenService {
    private static final Logger log = LoggerFactory.getLogger(TokenService.class);

    @Value("${api.security.token.secret}")
    private String secret;

    private final GateUserRepository repository;

    public String generateToken(GateUser user) {
        return JWT.create()
                .withIssuer("gatekeeper-api")
                .withSubject(user.getCpf())
                .withExpiresAt(new Date(System.currentTimeMillis() + TimeUnit.HOURS.toMillis(1)))
                .sign(Algorithm.HMAC256(secret));
    }

    public boolean checkToken(GateUser user, String token) {
        try {
            String subject = verifyAndGetSubject(token);
            return subject.equals(user.getCpf());
        } catch (Exception ex)  {
            log.error("Error while checking token", ex);
            return false;
        }
    }

    public boolean validateToken(String token) {
        try {
            verifyAndGetSubject(token);
            return true;
        } catch (Exception ex) {
            log.error("Invalid token", ex);
            return false;
        }
    }

    public String getSubject(String token) {
        return verifyAndGetSubject(token);
    }

    public UsernamePasswordAuthenticationToken getAuthentication(String token) {
        String userCPF = verifyAndGetSubject(token);
        GateUser user = repository.findByCpf(userCPF).orElseThrow();
        return new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
    }

    public GateUser getUserDetails(String token) {
        String userCPF = verifyAndGetSubject(token);
        return repository.findByCpf(userCPF).orElseThrow();
    }

    private String verifyAndGetSubject(String token) {
        return JWT.require(Algorithm.HMAC256(secret))
                .withIssuer("gatekeeper-api")
                .build()
                .verify(token)
                .getSubject();
    }
}