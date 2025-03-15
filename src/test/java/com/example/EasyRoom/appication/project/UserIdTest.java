package com.example.EasyRoom.appication.project;

import org.junit.jupiter.api.Test;

import com.example.EasyRoom.model.project.UserId;
import com.example.EasyRoom.model.project.ValidateContainException;

import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

class UserIdTest {

    @Test
    @DisplayName("Should create UserId with valid value")
    void shouldCreateUserId() {
        // Arrange
        String id = "user-123";

        // Act
        UserId userId = new UserId(id);

        // Assert
        assertNotNull(userId);
        assertEquals(id, userId.getValue());
    }

    @Test
    @DisplayName("Should test all equality scenarios")
    void shouldTestEquality() {
        // Same instance
        UserId id1 = new UserId("user-123");
        assertTrue(id1.equals(id1));

        // Same value
        UserId id2 = new UserId("user-123");
        assertTrue(id1.equals(id2));

        // Null comparison
        assertFalse(id1.equals(null));

        // Different type
        assertFalse(id1.equals("user-123"));

        // Different value
        UserId id3 = new UserId("different-user");
        assertFalse(id1.equals(id3));
    }

    @Test
    @DisplayName("Should test hashCode consistency")
    void shouldTestHashCode() {
        // Arrange
        UserId id1 = new UserId("user-123");
        UserId id2 = new UserId("user-123");
        UserId id3 = new UserId("different-user");

        // Assert
        assertEquals(id1.hashCode(), id2.hashCode());
        assertNotEquals(id1.hashCode(), id3.hashCode());
    }

    @Test
    @DisplayName("Should throw exception for null value")
    void shouldThrowExceptionForNullValue() {
        ValidateContainException exception = assertThrows(ValidateContainException.class, () -> {
            new UserId(null);
        });
        assertEquals("User ID cannot be empty", exception.getMessage());
    }

    @Test
    @DisplayName("Should throw exception for empty value")
    void shouldThrowExceptionForEmptyValue() {
        ValidateContainException exception = assertThrows(ValidateContainException.class, () -> {
            new UserId("");
        });
        assertEquals("User ID cannot be empty", exception.getMessage());
    }

    @Test
    @DisplayName("Should throw exception for blank value")
    void shouldThrowExceptionForBlankValue() {
        ValidateContainException exception = assertThrows(ValidateContainException.class, () -> {
            new UserId("   ");
        });
        assertEquals("User ID cannot be empty", exception.getMessage());
    }
}