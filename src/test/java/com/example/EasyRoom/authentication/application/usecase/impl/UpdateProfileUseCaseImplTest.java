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
class UpdateProfileUseCaseImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserDomainService userDomainService;

    @InjectMocks
    private UpdateProfileUseCaseImpl updateProfileUseCase;

    private UserRequestDto requestDto;

    @BeforeEach
    void setUp() {
        // Setup global pour tous les tests (partie du Sandwich Pattern)
        requestDto = new UserRequestDto();
        requestDto.setEmail("new@example.com");
        requestDto.setPassword("newpassword123");
    }

    @Test
    void execute_shouldUpdateProfileSuccessfully() {
        // 1. Setup (Arrange) : Préparer les données et configurer les mocks
        UserAggregate user = new UserAggregate(new Email("old@example.com"), new Password("oldpassword"));
        user.setId(1L);
        when(userRepository.findById(1L)).thenReturn(user);
        when(userRepository.save(any(UserAggregate.class))).thenReturn(user);

        // 2. Exercise (Act) : Exécuter l'action à tester
        UserResponseDto response = updateProfileUseCase.execute(1L, requestDto);

        // 3. Verify (Assert) : Vérifier les résultats attendus
        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertEquals("new@example.com", response.getEmail());
        verify(userDomainService).validateEmail("new@example.com");
        verify(userDomainService).validatePassword("newpassword123");
        verify(userRepository).findById(1L);
        verify(userRepository).save(any(UserAggregate.class));

        // Teardown implicite : Mockito nettoie automatiquement les mocks
    }

    @Test
    void execute_shouldThrowExceptionWhenUserIdIsNull() {
        // 1. Setup (Arrange)
        Long userId = null;

        // 2. Exercise (Act) & Verify (Assert)
        DomainException exception = assertThrows(DomainException.class, () -> updateProfileUseCase.execute(userId, requestDto));
        assertEquals("Failed to update profile: User ID cannot be null", exception.getMessage());
        verify(userRepository, never()).findById(anyLong());
        verify(userRepository, never()).save(any(UserAggregate.class));

        // Teardown implicite
    }

    @Test
    void execute_shouldThrowExceptionWhenUserNotFound() {
        // 1. Setup (Arrange)
        when(userRepository.findById(1L)).thenReturn(null);

        // 2. Exercise (Act) & Verify (Assert)
        DomainException exception = assertThrows(DomainException.class, () -> updateProfileUseCase.execute(1L, requestDto));
        assertEquals("Failed to update profile: User not found", exception.getMessage());
        verify(userRepository).findById(1L);
        verify(userRepository, never()).save(any(UserAggregate.class));

        // Teardown implicite
    }
}