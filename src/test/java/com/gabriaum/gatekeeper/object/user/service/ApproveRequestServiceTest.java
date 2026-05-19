package com.gabriaum.gatekeeper.object.user.service;

import com.gabriaum.gatekeeper.auth.AuthenticationRequest;
import com.gabriaum.gatekeeper.auth.repository.AuthenticationRequestRepository;
import com.gabriaum.gatekeeper.user.GateUser;
import com.gabriaum.gatekeeper.user.factory.GateUserFactory;
import com.gabriaum.gatekeeper.user.repository.GateUserRepository;
import com.gabriaum.gatekeeper.user.service.ApproveRequestService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ApproveRequestServiceTest {
    @Mock
    private AuthenticationRequestRepository requestRepository;

    @Mock
    private GateUserRepository repository;

    @Mock
    private GateUserFactory factory;

    @InjectMocks
    private ApproveRequestService service;

    @Test
    void shouldApproveRequestSuccessfully() {
        Long id = 1L;

        AuthenticationRequest request = new AuthenticationRequest();
        GateUser user = new GateUser();

        when(requestRepository.findById(id))
                .thenReturn(Optional.of(request));

        when(factory.createByRequest(request))
                .thenReturn(user);

        service.approveRequest(id);

        verify(repository).save(user);
        verify(requestRepository).deleteById(id);
    }

    @Test
    void shouldThrowWhenRequestNotFound() {
        Long id = 1L;

        when(requestRepository.findById(id))
                .thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class,
                () -> service.approveRequest(id)
        );

        verify(repository, never()).save(any());
        verify(requestRepository, never()).deleteById(any());
    }
}