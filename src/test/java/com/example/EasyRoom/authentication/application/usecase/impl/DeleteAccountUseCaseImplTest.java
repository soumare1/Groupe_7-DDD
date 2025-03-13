package com.example.EasyRoom.authentication.application.usecase.impl;

import com.example.EasyRoom.authentication.domain.aggregate.UserAggregate;
import com.example.EasyRoom.authentication.domain.repository.UserRepository;
import com.example.EasyRoom.authentication.domain.event.UserDeletedEvent;
import com.example.EasyRoom.shared.event.EventPublisher;
import com.example.EasyRoom.shared.exception.DomainException;
import org.junit.jupiter.api.BeforeEach;
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
        // Arrange
        UserAggregate user = new UserAggregate(null, null); // Email et Password peuvent être null pour ce test
        user.setId(1L);
        when(userRepository.findById(1L)).thenReturn(user);

        // Act
        deleteAccountUseCase.execute(1L);

        // Assert
        verify(userRepository).findById(1L);
        verify(userRepository).delete(user);
        verify(eventPublisher).publish(eq(new UserDeletedEvent(1L))); // Vérifie l'égalité logique
    }

    @Test
    void execute_shouldThrowExceptionWhenUserIdIsNull() {
        // Act & Assert
        DomainException exception = assertThrows(DomainException.class, () -> deleteAccountUseCase.execute(null));
        assertEquals("Failed to delete account: User ID cannot be null", exception.getMessage());
        verify(userRepository, never()).findById(anyLong());
        verify(userRepository, never()).delete(any(UserAggregate.class));
        verify(eventPublisher, never()).publish(any());
    }

    @Test
    void execute_shouldThrowExceptionWhenUserNotFound() {
        // Arrange
        when(userRepository.findById(1L)).thenReturn(null);

        // Act & Assert
        DomainException exception = assertThrows(DomainException.class, () -> deleteAccountUseCase.execute(1L));
        assertEquals("Failed to delete account: User not found", exception.getMessage());
        verify(userRepository).findById(1L);
        verify(userRepository, never()).delete(any(UserAggregate.class));
        verify(eventPublisher, never()).publish(any());
    }
}