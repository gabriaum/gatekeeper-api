package com.gabriaum.gatekeeper.object.user;

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
}