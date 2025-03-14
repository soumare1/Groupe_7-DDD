package com.example.EasyRoom.appication.project;


import org.junit.jupiter.api.Test;

import com.example.EasyRoom.model.project.ProjectId;

import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

class ProjectIdTest {

    @Test
    @DisplayName("Should create ProjectId with valid value")
    void shouldCreateProjectId() {
        // Arrange
        String id = "test-id-123";

        // Act
        ProjectId projectId = new ProjectId(id);

        // Assert
        assertNotNull(projectId);
        assertEquals(id, projectId.getValue());
    }

    @Test
    @DisplayName("Should generate unique ProjectId")
    void shouldGenerateUniqueId() {
        // Act
        ProjectId id1 = ProjectId.generate();
        ProjectId id2 = ProjectId.generate();

        // Assert
        assertNotNull(id1);
        assertNotNull(id2);
        assertNotEquals(id1.getValue(), id2.getValue());
    }

    @Test
    @DisplayName("Should test all equality scenarios")
    void shouldTestEquality() {
        // Same instance
        ProjectId id1 = new ProjectId("test-id-123");
        assertTrue(id1.equals(id1));

        // Same value
        ProjectId id2 = new ProjectId("test-id-123");
        assertTrue(id1.equals(id2));

        // Null comparison
        assertFalse(id1.equals(null));

        // Different type
        assertFalse(id1.equals("test-id-123"));

        // Different value
        ProjectId id3 = new ProjectId("different-id");
        assertFalse(id1.equals(id3));
    }

    @Test
    @DisplayName("Should test hashCode consistency")
    void shouldTestHashCode() {
        // Arrange
        ProjectId id1 = new ProjectId("test-id-123");
        ProjectId id2 = new ProjectId("test-id-123");
        ProjectId id3 = new ProjectId("different-id");

        // Assert
        assertEquals(id1.hashCode(), id2.hashCode());
        assertNotEquals(id1.hashCode(), id3.hashCode());
    }

    @Test
    @DisplayName("Should throw exception for null value")
    void shouldThrowExceptionForNullValue() {
        assertThrows(IllegalArgumentException.class, () -> {
            new ProjectId(null);
        });
    }

    @Test
    @DisplayName("Should throw exception for empty value")
    void shouldThrowExceptionForEmptyValue() {
        assertThrows(IllegalArgumentException.class, () -> {
            new ProjectId("");
        });
    }

    @Test
    @DisplayName("Should throw exception for blank value")
    void shouldThrowExceptionForBlankValue() {
        assertThrows(IllegalArgumentException.class, () -> {
            new ProjectId("   ");
        });
    }
}