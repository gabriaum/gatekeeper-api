package com.gabriaum.gatekeeper.object.user.factory;

import com.gabriaum.gatekeeper.object.auth.AuthenticationRequest;
import com.gabriaum.gatekeeper.object.user.GateUser;
import org.springframework.stereotype.Component;

@Component
public class GateUserFactory {
    public GateUser createByRequest(AuthenticationRequest request) {
        GateUser gateUser = new GateUser();
        gateUser.setCpf(request.getCpf());
        gateUser.setPassword(request.getPassword());
        gateUser.setEmail(request.getEmail());
        return gateUser;
    }
}