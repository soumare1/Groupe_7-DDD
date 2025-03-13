package com.example.EasyRoom.authentication.application.usecase.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DeleteAccountUseCaseImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private EventPublisher eventPublisher;

    @InjectMocks
    private DeleteAccountUseCaseImpl deleteAccountUseCase;

    @Test
    void execute_shouldDeleteAccountSuccessfully() {
        // 1. Setup (Arrange) : Préparer les données et configurer les mocks
        UserAggregate user = new UserAggregate(null, null); // Email et Password peuvent être null pour ce test
        user.setId(1L);
        when(userRepository.findById(1L)).thenReturn(user);

        // 2. Exercise (Act) : Exécuter l'action à tester
        deleteAccountUseCase.execute(1L);

        // 3. Verify (Assert) : Vérifier les résultats attendus
        verify(userRepository).findById(1L);
        verify(userRepository).delete(user);
        verify(eventPublisher).publish(eq(new UserDeletedEvent(1L)));

        // Teardown implicite : Mockito nettoie automatiquement les mocks
    }

    @Test
    void execute_shouldThrowExceptionWhenUserIdIsNull() {
        // 1. Setup (Arrange)
        Long userId = null;

        // 2. Exercise (Act) & Verify (Assert)
        DomainException exception = assertThrows(DomainException.class, () -> deleteAccountUseCase.execute(userId));
        assertEquals("Failed to delete account: User ID cannot be null", exception.getMessage());
        verify(userRepository, never()).findById(anyLong());
        verify(userRepository, never()).delete(any(UserAggregate.class));
        verify(eventPublisher, never()).publish(any());

        // Teardown implicite
    }

    @Test
    void execute_shouldThrowExceptionWhenUserNotFound() {
        // 1. Setup (Arrange)
        when(userRepository.findById(1L)).thenReturn(null);

        // 2. Exercise (Act) & Verify (Assert)
        DomainException exception = assertThrows(DomainException.class, () -> deleteAccountUseCase.execute(1L));
        assertEquals("Failed to delete account: User not found", exception.getMessage());
        verify(userRepository).findById(1L);
        verify(userRepository, never()).delete(any(UserAggregate.class));
        verify(eventPublisher, never()).publish(any());

        // Teardown implicite
    }
}