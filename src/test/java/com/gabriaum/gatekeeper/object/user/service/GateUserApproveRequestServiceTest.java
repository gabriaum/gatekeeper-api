package com.gabriaum.gatekeeper.object.user.service;

import com.gabriaum.gatekeeper.object.auth.AuthenticationRequest;
import com.gabriaum.gatekeeper.object.auth.repository.AuthenticationRequestRepository;
import com.gabriaum.gatekeeper.object.user.GateUser;
import com.gabriaum.gatekeeper.object.user.factory.GateUserFactory;
import com.gabriaum.gatekeeper.object.user.repository.GateUserRepository;
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
class GateUserApproveRequestServiceTest {
    @Mock
    private AuthenticationRequestRepository requestRepository;

    @Mock
    private GateUserRepository repository;

    @Mock
    private GateUserFactory factory;

    @InjectMocks
    private GateUserApproveRequestService service;

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