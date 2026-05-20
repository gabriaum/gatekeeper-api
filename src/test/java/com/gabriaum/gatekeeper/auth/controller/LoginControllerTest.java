package com.gabriaum.gatekeeper.auth.controller;

import com.gabriaum.gatekeeper.auth.service.LoginService;
import com.gabriaum.gatekeeper.infra.security.service.TokenService;
import com.gabriaum.gatekeeper.user.GateUser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.web.context.WebApplicationContext;

import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
class LoginControllerTest {
    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;

    @MockitoBean
    private LoginService loginService;

    @MockitoBean
    private TokenService tokenService;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context)
                .apply(SecurityMockMvcConfigurers.springSecurity())
                .build();
    }

    @Test
    void shouldReturnTokenWhenCredentialsAreValid() throws Exception {
        GateUser gateUser = new GateUser();
        gateUser.setCpf("12345678900");

        when(loginService.validate(any())).thenReturn(gateUser);
        when(tokenService.generateToken(gateUser)).thenReturn("jwt-token");

        mockMvc.perform(post("/api/v1/authentication/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{" +
                                "\"cpf\":\"12345678900\"," +
                                "\"password\":\"senha\"" +
                                "}"))
                .andExpect(status().isOk())
                .andExpect(content().string("jwt-token"));
    }

    @Test
    void shouldReturnValidationErrorWhenPasswordIsMissing() throws Exception {
        mockMvc.perform(post("/api/v1/authentication/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{" +
                                "\"cpf\":\"12345678900\"" +
                                "}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errorCode").value("VALIDATION_ERROR"))
                .andExpect(jsonPath("$.violations[0].field").value("password"));
    }
}
