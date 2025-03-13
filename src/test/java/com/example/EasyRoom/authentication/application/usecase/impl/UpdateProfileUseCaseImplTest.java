package com.example.EasyRoom.authentication.application.usecase.impl;

import com.example.EasyRoom.authentication.application.dto.UserRequestDto;
import com.example.EasyRoom.authentication.application.dto.UserResponseDto;
import com.example.EasyRoom.authentication.domain.aggregate.UserAggregate;
import com.example.EasyRoom.authentication.domain.repository.UserRepository;
import com.example.EasyRoom.authentication.domain.service.UserDomainService;
import com.example.EasyRoom.authentication.domain.valueobject.Email;
import com.example.EasyRoom.authentication.domain.valueobject.Password;
import com.example.EasyRoom.shared.exception.DomainException;
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
        requestDto = new UserRequestDto();
        requestDto.setEmail("new@example.com");
        requestDto.setPassword("newpassword123");
    }

    @Test
    void execute_shouldUpdateProfileSuccessfully() {
        // Arrange
        UserAggregate user = new UserAggregate(new Email("old@example.com"), new Password("oldpassword"));
        user.setId(1L);
        when(userRepository.findById(1L)).thenReturn(user);
        when(userRepository.save(any(UserAggregate.class))).thenReturn(user);

        // Act
        UserResponseDto response = updateProfileUseCase.execute(1L, requestDto);

        // Assert
        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertEquals("new@example.com", response.getEmail());
        verify(userDomainService).validateEmail("new@example.com");
        verify(userDomainService).validatePassword("newpassword123");
        verify(userRepository).findById(1L);
        verify(userRepository).save(any(UserAggregate.class));
    }

    @Test
    void execute_shouldThrowExceptionWhenUserIdIsNull() {
        // Act & Assert
        DomainException exception = assertThrows(DomainException.class, () -> updateProfileUseCase.execute(null, requestDto));
        assertEquals("Failed to update profile: User ID cannot be null", exception.getMessage());
        verify(userRepository, never()).findById(anyLong());
        verify(userRepository, never()).save(any(UserAggregate.class));
    }

    @Test
    void execute_shouldThrowExceptionWhenUserNotFound() {
        // Arrange
        when(userRepository.findById(1L)).thenReturn(null);

        // Act & Assert
        DomainException exception = assertThrows(DomainException.class, () -> updateProfileUseCase.execute(1L, requestDto));
        assertEquals("Failed to update profile: User not found", exception.getMessage());
        verify(userRepository).findById(1L);
        verify(userRepository, never()).save(any(UserAggregate.class));
    }
}