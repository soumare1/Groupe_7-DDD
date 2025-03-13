package com.example.EasyRoom.authentication.application.usecase.impl;

import com.example.EasyRoom.authentication.application.dto.UserRequestDto;
import com.example.EasyRoom.authentication.application.dto.UserResponseDto;
import com.example.EasyRoom.authentication.domain.aggregate.UserAggregate;
import com.example.EasyRoom.authentication.domain.repository.UserRepository;
import com.example.EasyRoom.authentication.domain.service.UserDomainService;
import com.example.EasyRoom.authentication.domain.valueobject.Email;
import com.example.EasyRoom.authentication.domain.valueobject.Password;
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
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CreateUserUseCaseImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserDomainService userDomainService;

    @Mock
    private EventPublisher eventPublisher;

    @InjectMocks
    private CreateUserUseCaseImpl createUserUseCase;

    private UserRequestDto requestDto;

    @BeforeEach
    void setUp() {
        requestDto = new UserRequestDto();
        requestDto.setEmail("test@example.com");
        requestDto.setPassword("password123");
    }

    @Test
    void execute_shouldCreateUserSuccessfully() {
        // Arrange
        UserAggregate savedUser = new UserAggregate(new Email("test@example.com"), new Password("password123"));
        savedUser.setId(1L);

        when(userRepository.findByEmail(any(Email.class))).thenReturn(null); // Email doesn't exist
        when(userRepository.save(any(UserAggregate.class))).thenReturn(savedUser);

        // Act
        UserResponseDto response = createUserUseCase.execute(requestDto);

        // Assert
        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertEquals("test@example.com", response.getEmail());
        verify(userDomainService).validateEmail("test@example.com");
        verify(userDomainService).validatePassword("password123");
        verify(userRepository).findByEmail(any(Email.class));
        verify(userRepository).save(any(UserAggregate.class));
        verify(eventPublisher).publish(any());
    }

    @Test
    void execute_shouldThrowExceptionWhenEmailAlreadyExists() {
        // Arrange
        UserAggregate existingUser = new UserAggregate(new Email("test@example.com"), new Password("password123"));
        when(userRepository.findByEmail(any(Email.class))).thenReturn(existingUser);

        // Act & Assert
        DomainException exception = assertThrows(DomainException.class, () -> createUserUseCase.execute(requestDto));
        assertEquals("Failed to create user: Email already exists", exception.getMessage());
        verify(userDomainService).validateEmail("test@example.com");
        verify(userDomainService).validatePassword("password123");
        verify(userRepository).findByEmail(any(Email.class));
        verify(userRepository, never()).save(any(UserAggregate.class));
        verify(eventPublisher, never()).publish(any());
    }

    @Test
    void execute_shouldThrowExceptionWhenEmailValidationFails() {
        // Arrange
        doThrow(new IllegalArgumentException("Invalid email format")).when(userDomainService).validateEmail("test@example.com");

        // Act & Assert
        DomainException exception = assertThrows(DomainException.class, () -> createUserUseCase.execute(requestDto));
        assertEquals("Failed to create user: Invalid email format", exception.getMessage());
        verify(userDomainService).validateEmail("test@example.com");
        verify(userDomainService, never()).validatePassword(anyString());
        verify(userRepository, never()).findByEmail(any(Email.class));
        verify(userRepository, never()).save(any(UserAggregate.class));
        verify(eventPublisher, never()).publish(any());
    }
}