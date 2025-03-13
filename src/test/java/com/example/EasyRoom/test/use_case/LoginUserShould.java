package com.example.EasyRoom.test.use_case;

import com.example.EasyRoom.model.authentication.User;
import com.example.EasyRoom.model.authentication.UserRepository;
import com.example.EasyRoom.model.authentication.UserValidator;
import com.example.EasyRoom.use_case.LoginUser;
import com.example.EasyRoom.use_case.UserNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LoginUserShould {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserValidator userValidator;

    @InjectMocks
    private LoginUser loginUser;

    private User mockUser;

    @BeforeEach
    void setUp() {
        mockUser = new User("test@example.com", "password123");
        mockUser.setId(1L);
    }

    @Test
    void loginSuccessfully() {
        // 1. Setup (Arrange): Préparer les données et configurer les mocks
        when(userRepository.findByEmail("test@example.com")).thenReturn(mockUser);
        doNothing().when(userValidator).validateEmail("test@example.com");
        doNothing().when(userValidator).validatePassword("password123");

        // 2. Exercise (Act): Exécuter l'action à tester
        User result = loginUser.execute("test@example.com", "password123");

        // 3. Verify (Assert): Vérifier les résultats attendus
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("test@example.com", result.getEmail());
        verify(userRepository).findByEmail("test@example.com");
        verify(userValidator).validateEmail("test@example.com");
        verify(userValidator).validatePassword("password123");
    }

    @Test
    void throwUserNotFoundExceptionWhenUserDoesNotExist() {
        // 1. Setup (Arrange): Préparer les données et configurer les mocks
        when(userRepository.findByEmail(anyString())).thenReturn(null);
        doNothing().when(userValidator).validateEmail(anyString());
        doNothing().when(userValidator).validatePassword(anyString());

        // 2. Exercise (Act) & 3. Verify (Assert): Exécuter et vérifier l'exception
        assertThrows(UserNotFoundException.class, () -> loginUser.execute("test@example.com", "password123"));
        verify(userRepository).findByEmail("test@example.com");
        verify(userValidator).validateEmail("test@example.com");
        verify(userValidator).validatePassword("password123");
    }

    @Test
    void throwIllegalArgumentExceptionWhenEmailIsInvalid() {
        // 1. Setup (Arrange): Préparer les données et configurer les mocks
        doThrow(new IllegalArgumentException("Email cannot be null or empty")).when(userValidator).validateEmail(null);
        // Supprimer le stubbing inutile de validatePassword
        // doNothing().when(userValidator).validatePassword(anyString()); // Supprimé

        // 2. Exercise (Act) & 3. Verify (Assert): Exécuter et vérifier l'exception
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> loginUser.execute(null, "password123"));
        assertEquals("Email cannot be null or empty", exception.getMessage());
        verify(userValidator).validateEmail(null);
        verify(userRepository, never()).findByEmail(anyString());
        verify(userValidator, never()).validatePassword(anyString());
    }

    @Test
    void throwIllegalArgumentExceptionWhenPasswordIsInvalid() {
        // 1. Setup (Arrange): Préparer les données et configurer les mocks
        doNothing().when(userValidator).validateEmail("test@example.com");
        doThrow(new IllegalArgumentException("Password cannot be null or empty")).when(userValidator).validatePassword(null);

        // 2. Exercise (Act) & 3. Verify (Assert): Exécuter et vérifier l'exception
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> loginUser.execute("test@example.com", null));
        assertEquals("Password cannot be null or empty", exception.getMessage());
        verify(userValidator).validateEmail("test@example.com");
        verify(userRepository, never()).findByEmail(anyString());
        verify(userValidator).validatePassword(null);
    }

    @Test
    void throwIllegalArgumentExceptionWhenPasswordIsIncorrect() {
        // 1. Setup (Arrange): Préparer les données et configurer les mocks
        when(userRepository.findByEmail("test@example.com")).thenReturn(mockUser);
        doNothing().when(userValidator).validateEmail("test@example.com");
        doNothing().when(userValidator).validatePassword("wrongpassword");

        // 2. Exercise (Act) & 3. Verify (Assert): Exécuter et vérifier l'exception
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> loginUser.execute("test@example.com", "wrongpassword"));
        assertEquals("Invalid password", exception.getMessage());
        verify(userRepository).findByEmail("test@example.com");
        verify(userValidator).validateEmail("test@example.com");
        verify(userValidator).validatePassword("wrongpassword");
    }
}