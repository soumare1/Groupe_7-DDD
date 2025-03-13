package com.example.EasyRoom.authentication.application.usecase.impl;

import com.example.EasyRoom.authentication.application.dto.UserResponseDto;
import com.example.EasyRoom.authentication.application.usecase.LogoutUserUseCase;
import com.example.EasyRoom.authentication.domain.service.SessionService;
import com.example.EasyRoom.shared.exception.DomainException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LogoutUserUseCaseImplTest {

    @Mock
    private SessionService sessionService;

    @InjectMocks
    private LogoutUserUseCaseImpl logoutUserUseCase;

    @Test
    void execute_shouldLogoutSuccessfully() {
        // Act
        UserResponseDto response = logoutUserUseCase.execute(1L);

        // Assert
        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertNull(response.getEmail());
        verify(sessionService).invalidateSession(1L);
    }

    @Test
    void execute_shouldThrowExceptionWhenUserIdIsNull() {
        // Act & Assert
        DomainException exception = assertThrows(DomainException.class, () -> logoutUserUseCase.execute(null));
        assertEquals("Logout failed: User ID cannot be null", exception.getMessage());
        verify(sessionService, never()).invalidateSession(anyLong());
    }
}