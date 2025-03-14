package com.example.EasyRoom.appication.project;


import org.junit.jupiter.api.Test;

import com.example.EasyRoom.model.project.ProjectDescription;

import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

public class ProjectDescriptionTest {
    @Test
    @DisplayName("Should create project description with valid data")
    void shouldCreateProjectDescription() {
        // Arrange
        String description = "Test Description";

        // Act
        ProjectDescription projectDescription = new ProjectDescription(description);

        // Assert
        assertNotNull(projectDescription);
        assertEquals(description, projectDescription.getValue());
    }

    @Test
    @DisplayName("Should throw exception when creating project description with null value")
    void shouldThrowExceptionWithNullValue() {
        assertThrows(IllegalArgumentException.class, () -> {
            new ProjectDescription(null);
        });
    }

    @Test
    @DisplayName("Should test equality scenarios for ProjectDescription")
    void shouldTestEquality() {
        // Test same instance
        ProjectDescription desc1 = new ProjectDescription("Test Description");
        assertTrue(desc1.equals(desc1));

        // Test same value
        ProjectDescription desc2 = new ProjectDescription("Test Description");
        assertTrue(desc1.equals(desc2));

        // Test null
        assertFalse(desc1.equals(null));

        // Test different type
        assertFalse(desc1.equals("Test Description"));

        // Test different value
        ProjectDescription desc3 = new ProjectDescription("Different Description");
        assertFalse(desc1.equals(desc3));
    }

    @Test
    @DisplayName("Should test hashCode consistency")
    void shouldTestHashCode() {
        // Arrange
        ProjectDescription desc1 = new ProjectDescription("Test Description");
        ProjectDescription desc2 = new ProjectDescription("Test Description");
        ProjectDescription desc3 = new ProjectDescription("Different Description");

        // Assert
        assertEquals(desc1.hashCode(), desc2.hashCode());
        assertNotEquals(desc1.hashCode(), desc3.hashCode());
    }
}