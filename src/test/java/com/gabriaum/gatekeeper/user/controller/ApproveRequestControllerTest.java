package com.gabriaum.gatekeeper.user.controller;

import com.gabriaum.gatekeeper.user.service.ApproveRequestService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class ApproveRequestControllerTest {

    @Autowired
    private ApproveRequestController controller;

    @MockitoBean
    private ApproveRequestService approveRequestService;

    @Test
    @WithMockUser(roles = "ADMIN")
    void shouldApproveRequestForAdminUser() {
        controller.approve(1L);

        verify(approveRequestService).approveRequest(1L);
    }

    @Test
    @WithMockUser(roles = "USER")
    void shouldRejectNonAdminUser() {
        assertThrows(AccessDeniedException.class, () -> controller.approve(1L));

        verifyNoInteractions(approveRequestService);
    }
}
