package com.example.EasyRoom.authentication.application.usecase.impl;

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
        // 1. Setup (Arrange) : Préparer les données et configurer les mocks
        Long userId = 1L;

        // 2. Exercise (Act) : Exécuter l'action à tester
        UserResponseDto response = logoutUserUseCase.execute(userId);

        // 3. Verify (Assert) : Vérifier les résultats attendus
        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertNull(response.getEmail());
        verify(sessionService).invalidateSession(1L);

        // Teardown implicite : Mockito nettoie automatiquement les mocks
    }

    @Test
    void execute_shouldThrowExceptionWhenUserIdIsNull() {
        // 1. Setup (Arrange)
        Long userId = null;

        // 2. Exercise (Act) & Verify (Assert)
        DomainException exception = assertThrows(DomainException.class, () -> logoutUserUseCase.execute(userId));
        assertEquals("Logout failed: User ID cannot be null", exception.getMessage());
        verify(sessionService, never()).invalidateSession(anyLong());

        // Teardown implicite
    }
}