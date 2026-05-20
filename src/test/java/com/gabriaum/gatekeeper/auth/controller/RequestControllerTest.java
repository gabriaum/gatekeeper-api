package com.gabriaum.gatekeeper.auth.controller;

import com.gabriaum.gatekeeper.auth.AuthenticationRequest;
import com.gabriaum.gatekeeper.auth.AuthenticationRequestMapper;
import com.gabriaum.gatekeeper.auth.factory.ResponseFactory;
import com.gabriaum.gatekeeper.auth.repository.AuthenticationRequestRepository;
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
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
class RequestControllerTest {

    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;

    @MockitoBean
    private AuthenticationRequestRepository requestRepository;

    @MockitoBean
    private AuthenticationRequestMapper requestMapper;

    @MockitoBean
    private ResponseFactory requestResponseFactory;

    @org.junit.jupiter.api.BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context)
                .apply(SecurityMockMvcConfigurers.springSecurity())
                .build();
    }

    @Test
    void shouldRegisterRequestSuccessfully() throws Exception {
        AuthenticationRequest request = new AuthenticationRequest();
        when(requestMapper.toEntityByDTO(any())).thenReturn(request);
        when(requestRepository.save(request)).thenReturn(request);

        mockMvc.perform(post("/api/v1/authentication/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{" +
                                "\"cpf\":\"12345678900\"," +
                                "\"email\":\"test@gabriaum.com\"," +
                                "\"password\":\"senha\"" +
                                "}"))
                .andExpect(status().isOk())
                .andExpect(content().string("A sua requisição de registro foi cadastrada com sucesso."));

        verify(requestMapper).toEntityByDTO(any());
        verify(requestRepository).save(request);
    }

    @Test
    void shouldReturnValidationErrorWhenRegistrationPayloadIsIncomplete() throws Exception {
        mockMvc.perform(post("/api/v1/authentication/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{" +
                                "\"cpf\":\"12345678900\"," +
                                "\"email\":\"test@gabriaum.com\"" +
                                "}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errorCode").value("VALIDATION_ERROR"))
                .andExpect(jsonPath("$.violations[0].field").value("password"));
    }

    @Test
    void shouldRequireAdminRoleToListRequests() throws Exception {
        mockMvc.perform(get("/api/v1/authentication"))
                .andExpect(status().isUnauthorized());
    }
}
