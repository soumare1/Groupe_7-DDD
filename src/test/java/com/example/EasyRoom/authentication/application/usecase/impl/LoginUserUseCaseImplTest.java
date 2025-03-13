package com.example.EasyRoom.authentication.application.usecase.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LoginUserUseCaseImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserDomainService userDomainService;

    @InjectMocks
    private LoginUserUseCaseImpl loginUserUseCase;

    private UserRequestDto requestDto;

    @BeforeEach
    void setUp() {
        // Setup global pour tous les tests (partie du Sandwich Pattern)
        requestDto = new UserRequestDto();
        requestDto.setEmail("test@example.com");
        requestDto.setPassword("password123");
    }

    @Test
    void execute_shouldLoginSuccessfully() {
        // 1. Setup (Arrange) : Préparer les données et configurer les mocks
        UserAggregate user = new UserAggregate(new Email("test@example.com"), new Password("password123"));
        user.setId(1L);
        when(userRepository.findByEmail(any(Email.class))).thenReturn(user);
        doNothing().when(userDomainService).validatePassword("password123");

        // 2. Exercise (Act) : Exécuter l'action à tester
        UserResponseDto response = loginUserUseCase.execute(requestDto);

        // 3. Verify (Assert) : Vérifier les résultats attendus
        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertEquals("test@example.com", response.getEmail());
        verify(userRepository).findByEmail(any(Email.class));
        verify(userDomainService).validatePassword("password123");

        // Teardown implicite : Mockito nettoie automatiquement les mocks
    }

    @Test
    void execute_shouldThrowExceptionWhenUserNotFound() {
        // 1. Setup (Arrange)
        when(userRepository.findByEmail(any(Email.class))).thenReturn(null);

        // 2. Exercise (Act) & Verify (Assert)
        DomainException exception = assertThrows(DomainException.class, () -> loginUserUseCase.execute(requestDto));
        assertEquals("Login failed: User not found", exception.getMessage());
        verify(userRepository).findByEmail(any(Email.class));
        verify(userDomainService, never()).validatePassword(anyString());

        // Teardown implicite
    }

    @Test
    void execute_shouldThrowExceptionWhenPasswordValidationFails() {
        // 1. Setup (Arrange)
        UserAggregate user = new UserAggregate(new Email("test@example.com"), new Password("password123"));
        when(userRepository.findByEmail(any(Email.class))).thenReturn(user);
        doThrow(new IllegalArgumentException("Invalid password")).when(userDomainService).validatePassword("password123");

        // 2. Exercise (Act) & Verify (Assert)
        DomainException exception = assertThrows(DomainException.class, () -> loginUserUseCase.execute(requestDto));
        assertEquals("Login failed: Invalid password", exception.getMessage());
        verify(userRepository).findByEmail(any(Email.class));
        verify(userDomainService).validatePassword("password123");

        // Teardown implicite
    }
}