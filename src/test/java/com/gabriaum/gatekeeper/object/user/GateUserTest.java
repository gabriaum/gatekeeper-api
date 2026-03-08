package com.gabriaum.gatekeeper.object.user;

import com.gabriaum.gatekeeper.object.user.enums.GateUserRole;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GateUserTest {
    @Test
    void shouldReturnCpfAsUsername() {
        GateUser gateUser = new GateUser();
        gateUser.setCpf("123456789");

        String gateUsername = gateUser.getUsername();
        assertEquals("123456789", gateUsername);
    }

    @Test
    void shouldReturnRoleAuthority() {
        GateUser gateUser = new GateUser();
        gateUser.setRole(GateUserRole.ADMIN);

        String authority = gateUser.getAuthorities()
                .iterator()
                .next()
                .getAuthority();

        assertEquals("ROLE_ADMIN", authority);
    }
}