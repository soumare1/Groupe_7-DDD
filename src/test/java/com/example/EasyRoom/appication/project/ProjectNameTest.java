package com.example.EasyRoom.appication.project;


import org.junit.jupiter.api.Test;

import com.example.EasyRoom.model.project.ProjectName;
import com.example.EasyRoom.model.project.ValidateContainException;

import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

class ProjectNameTest {

    @Test
    @DisplayName("Should create project name with valid value")
    void shouldCreateProjectName() {
        String name = "Project Alpha";
        ProjectName projectName = new ProjectName(name);
        assertEquals(name, projectName.getValue());
    }

    @Test
    @DisplayName("Should throw exception for null value")
    void shouldThrowExceptionForNullValue() {
        assertThrows(ValidateContainException.class, () -> {
            new ProjectName(null);
        });
    }

    @Test
    @DisplayName("Should throw exception for empty value")
    void shouldThrowExceptionForEmptyValue() {
        assertThrows(ValidateContainException.class, () -> {
            new ProjectName("");
        });
        assertThrows(ValidateContainException.class, () -> {
            new ProjectName("   ");
        });
    }

    @Test
    @DisplayName("Should throw exception for too short name")
    void shouldThrowExceptionForTooShortValue() {
        assertThrows(ValidateContainException.class, () -> {
            new ProjectName("Ab");
        });
    }

    @Test
    @DisplayName("Should throw exception for too long name")
    void shouldThrowExceptionForTooLongValue() {
        String tooLong = "A".repeat(51);
        assertThrows(ValidateContainException.class, () -> {
            new ProjectName(tooLong);
        });
    }

    @Test
    @DisplayName("Should throw exception for invalid characters")
    void shouldThrowExceptionForInvalidCharacters() {
        assertThrows(ValidateContainException.class, () -> {
            new ProjectName("Project@123");
        });
        assertThrows(ValidateContainException.class, () -> {
            new ProjectName("Project_Name");
        });
    }

    @Test
    @DisplayName("Should throw exception for invalid start character")
    void shouldThrowExceptionForInvalidStartCharacter() {
        assertThrows(ValidateContainException.class, () -> {
            new ProjectName("123Project");
        });
        assertThrows(ValidateContainException.class, () -> {
            new ProjectName("-Project");
        });
    }

    @Test
    @DisplayName("Should test equality scenarios")
    void shouldTestEquality() {
        ProjectName name1 = new ProjectName("Project Alpha");
        ProjectName name2 = new ProjectName("Project Alpha");
        ProjectName name3 = new ProjectName("Project Beta");

        assertTrue(name1.equals(name1));
        assertTrue(name1.equals(name2));
        assertFalse(name1.equals(null));
        assertFalse(name1.equals("Project Alpha"));
        assertFalse(name1.equals(name3));
    }

    @Test
    @DisplayName("Should test hashCode consistency")
    void shouldTestHashCode() {
        ProjectName name1 = new ProjectName("Project Alpha");
        ProjectName name2 = new ProjectName("Project Alpha");
        ProjectName name3 = new ProjectName("Project Beta");

        assertEquals(name1.hashCode(), name2.hashCode());
        assertNotEquals(name1.hashCode(), name3.hashCode());
    }

}