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
        requestDto = new UserRequestDto();
        requestDto.setEmail("test@example.com");
        requestDto.setPassword("password123");
    }

    @Test
    void execute_shouldLoginSuccessfully() {
        // Arrange
        UserAggregate user = new UserAggregate(new Email("test@example.com"), new Password("password123"));
        user.setId(1L);

        when(userRepository.findByEmail(any(Email.class))).thenReturn(user);

        // Act
        UserResponseDto response = loginUserUseCase.execute(requestDto);

        // Assert
        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertEquals("test@example.com", response.getEmail());
        verify(userDomainService).validateEmail("test@example.com");
        verify(userDomainService).validatePassword("password123");
        verify(userRepository).findByEmail(any(Email.class));
    }

    @Test
    void execute_shouldThrowExceptionWhenCredentialsAreInvalid() {
        // Arrange
        UserAggregate user = new UserAggregate(new Email("test@example.com"), new Password("wrongpassword"));
        when(userRepository.findByEmail(any(Email.class))).thenReturn(user);

        // Act & Assert
        DomainException exception = assertThrows(DomainException.class, () -> loginUserUseCase.execute(requestDto));
        assertEquals("Login failed: Invalid email or password", exception.getMessage());
        verify(userDomainService).validateEmail("test@example.com");
        verify(userDomainService).validatePassword("password123");
        verify(userRepository).findByEmail(any(Email.class));
    }

    @Test
    void execute_shouldThrowExceptionWhenUserNotFound() {
        // Arrange
        when(userRepository.findByEmail(any(Email.class))).thenReturn(null);

        // Act & Assert
        DomainException exception = assertThrows(DomainException.class, () -> loginUserUseCase.execute(requestDto));
        assertEquals("Login failed: Invalid email or password", exception.getMessage());
        verify(userDomainService).validateEmail("test@example.com");
        verify(userDomainService).validatePassword("password123");
        verify(userRepository).findByEmail(any(Email.class));
    }
}