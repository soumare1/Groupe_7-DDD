package com.example.EasyRoom.appication.project;


import org.junit.jupiter.api.Test;

import com.example.EasyRoom.model.project.ProjectName;

import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

class ProjectNameTest {

    @Test
    @DisplayName("Should create ProjectName with valid value")
    void shouldCreateProjectName() {
        // Arrange
        String name = "Test Project";

        // Act
        ProjectName projectName = new ProjectName(name);

        // Assert
        assertNotNull(projectName);
        assertEquals(name, projectName.getValue());
    }

    @Test
    @DisplayName("Should throw exception for null value")
    void shouldThrowExceptionForNullValue() {
        assertThrows(IllegalArgumentException.class, () -> {
            new ProjectName(null);
        });
    }

    @Test
    @DisplayName("Should throw exception for empty value")
    void shouldThrowExceptionForEmptyValue() {
        assertThrows(IllegalArgumentException.class, () -> {
            new ProjectName("");
        });
    }

    @Test
    @DisplayName("Should throw exception for blank value")
    void shouldThrowExceptionForBlankValue() {
        assertThrows(IllegalArgumentException.class, () -> {
            new ProjectName("   ");
        });
    }

    @Test
    @DisplayName("Should test all equality scenarios")
    void shouldTestEquality() {
        // Same instance
        ProjectName name1 = new ProjectName("Test Project");
        assertTrue(name1.equals(name1));

        // Same value
        ProjectName name2 = new ProjectName("Test Project");
        assertTrue(name1.equals(name2));

        // Null comparison
        assertFalse(name1.equals(null));

        // Different type
        assertFalse(name1.equals("Test Project"));

        // Different value
        ProjectName name3 = new ProjectName("Different Project");
        assertFalse(name1.equals(name3));
    }

    @Test
    @DisplayName("Should test hashCode consistency")
    void shouldTestHashCode() {
        // Arrange
        ProjectName name1 = new ProjectName("Test Project");
        ProjectName name2 = new ProjectName("Test Project");
        ProjectName name3 = new ProjectName("Different Project");

        // Assert
        assertEquals(name1.hashCode(), name2.hashCode());
        assertNotEquals(name1.hashCode(), name3.hashCode());
    }
}