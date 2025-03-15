package com.example.EasyRoom.appication.project;

import org.junit.jupiter.api.Test;

import com.example.EasyRoom.model.project.Project;
import com.example.EasyRoom.model.project.ValidateContainException;

import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

public class ProjectTest {

    @Test
    @DisplayName("Should reconstitute project with valid data")
    void shouldReconstituteProject() {
        // Arrange
        String id = "project-123";
        String name = "Test Project";
        String description = "Test Description , Should create project through use caseShould create project through use caseShould create project through use case";
        String userId = "user-123";

        // Act
        Project project = Project.reconstitute(id, name, description, userId);

        // Assert
        assertNotNull(project);
        assertEquals(id, project.getId());
        assertEquals(name, project.getName().getValue());
        assertEquals(description, project.getProjectDescription().getValue());
        assertEquals(userId, project.getUserId().getValue());
    }
    @Test
    @DisplayName("Should test all equals scenarios")
    void shouldTestEquality() {
        // Same instance
        Project project1 = Project.reconstitute("project-123", "name", "Test Description , Should create project through use caseShould create project through use caseShould create project through use case", "userId");
        assertTrue(project1.equals(project1));

        // Same values
        Project project2 = Project.reconstitute("project-123", "name", "Test Description , Should create project through use caseShould create project through use caseShould create project through use case", "userId");
        assertTrue(project1.equals(project2));

        // Null comparison
        assertFalse(project1.equals(null));

        // Different type
        assertFalse(project1.equals("project-123"));

        // Different values
        Project project3 = Project.reconstitute("other-id", "name", "Test Description , Should create project through use caseShould create project through use caseShould create project through use case", "userId");
        assertFalse(project1.equals(project3));
    }

    @Test
    @DisplayName("Should throw exception when reconstituting with null id")
    void shouldThrowExceptionWithNullId() {
        ValidateContainException exception = assertThrows(ValidateContainException.class, () -> {
            Project.reconstitute(null, "name", "desc", "userId");
        });

        assertNotNull(exception);   
    }

    @Test
    @DisplayName("Should throw exception when reconstituting with empty id")
    void shouldThrowExceptionWithEmptyId() {
        ValidateContainException exception = assertThrows(ValidateContainException.class, () -> {
            Project.reconstitute("", "name", "desc", "userId");
        });
        assertEquals("Project ID cannot be empty", exception.getMessage());
    }

    @Test
    @DisplayName("Should throw exception when reconstituting with blank id")
    void shouldThrowExceptionWithBlankId() {
        ValidateContainException exception = assertThrows(ValidateContainException.class, () -> {
            Project.reconstitute("   ", "name", "desc", "userId");
        });
        assertEquals("Project ID cannot be empty", exception.getMessage());
    }

    @Test
    @DisplayName("Should verify reconstituted project equals another with same values")
    void shouldVerifyReconstitutedProjectEquality() {
        // Arrange
        String id = "project-123";
        String name = "Test Project";
        String description = "Test DescriptionTest DescriptionTest\"Test DescriptionTest DescriptionTest DescriptionTest DescriptionTest DescriptionTest Description";
        String userId = "user-123";

        // Act
        Project project1 = Project.reconstitute(id, name, description, userId);
        Project project2 = Project.reconstitute(id, name, description, userId);

        // Assert
        assertEquals(project1, project2);
        assertEquals(project1.hashCode(), project2.hashCode());
    }


    @Test
    @DisplayName("Should verify reconstituted project does not equal another with different values")
    void shouldVerifyReconstitutedProjectInequality() {
        // Arrange
        String id = "project-123";
        String name = "Test Project";
        String description = "Test DescriptionTest DescriptionTest\"Test DescriptionTest DescriptionTest DescriptionTest DescriptionTest DescriptionTest Description";
        String userId = "user-123";

        // Act
        Project project1 = Project.reconstitute(id, name, description, userId);
        Project project2 = Project.reconstitute("other-id", name, description, userId);

        // Assert
        assertNotEquals(project1, project2);
        assertNotEquals(project1.hashCode(), project2.hashCode());
    }


}
