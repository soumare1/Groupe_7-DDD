package com.example.EasyRoom.appication.project;


import org.junit.jupiter.api.Test;

import com.example.EasyRoom.model.project.ProjectDescription;

import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;



public class ProjectDescriptionTest {
    @Test
    @DisplayName("Should create description with valid value")
    void shouldCreateValidDescription() {
        String validDescription = "This is a description of one hundred characters we need to test the limit of the description to see if it works properly now-123.";
        ProjectDescription description = new ProjectDescription(validDescription);
        assertEquals(validDescription, description.getValue());
    }

    @Test
    @DisplayName("Should throw exception for invalid punctuation")
    void shouldThrowExceptionForInvalidPunctuation() {
        assertThrows(IllegalArgumentException.class, () -> {
            new ProjectDescription("This description contains invalid punctuation!");
        });
        
        assertThrows(IllegalArgumentException.class, () -> {
            new ProjectDescription("This description contains commas, which are not allowed.");
        });
    }


    @Test
    @DisplayName("Should throw exception for null value")
    void shouldThrowExceptionForNullValue() {
        assertThrows(IllegalArgumentException.class, () -> {
            new ProjectDescription(null);
        });
    }

    @Test
    @DisplayName("Should throw exception for empty value")
    void shouldThrowExceptionForEmptyValue() {
        assertThrows(IllegalArgumentException.class, () -> {
            new ProjectDescription("");
        });
        assertThrows(IllegalArgumentException.class, () -> {
            new ProjectDescription("   ");
        });
    }

    @Test
    @DisplayName("Should throw exception for too short description")
    void shouldThrowExceptionForTooShortValue() {
        assertThrows(IllegalArgumentException.class, () -> {
            new ProjectDescription("Short.");
        });
    }

    @Test
    @DisplayName("Should throw exception for too long description")
    void shouldThrowExceptionForTooLongValue() {
        String tooLong = "A".repeat(1001) + ".";
        assertThrows(IllegalArgumentException.class, () -> {
            new ProjectDescription(tooLong);
        });
    }

    @Test
    @DisplayName("Should throw exception for invalid characters")
    void shouldThrowExceptionForInvalidCharacters() {
        assertThrows(IllegalArgumentException.class, () -> {
            new ProjectDescription("Invalid $ special @ characters.");
        });
    }

    @Test
    @DisplayName("Should throw exception for invalid structure")
    void shouldThrowExceptionForInvalidStructure() {
        assertThrows(IllegalArgumentException.class, () -> {
            new ProjectDescription("lowercase start is invalid.");
        });
        
        assertThrows(IllegalArgumentException.class, () -> {
            new ProjectDescription("Missing final period");
        });
    }

    @Test
    @DisplayName("Should test equality")
    void shouldTestEquality() {
        String validDescription = "This is a description of one hundred characters we need to test the limit of the description to see if it works properly now-123.";
        ProjectDescription desc1 = new ProjectDescription(validDescription);
        ProjectDescription desc2 = new ProjectDescription(validDescription);
        ProjectDescription desc3 = new ProjectDescription("This is a description of one hundred characters owe need to test the limit of the description to see if it works properly now-123.");

        assertTrue(desc1.equals(desc1));
        assertTrue(desc1.equals(desc2));
        assertFalse(desc1.equals(null));
        assertFalse(desc1.equals("not a description"));
        assertFalse(desc1.equals(desc3));
    }

    @Test
    @DisplayName("Should test hashCode consistency")
    void shouldTestHashCode() {
        String validDescription = "This is a description of one hundred characters we need to test the limit of the description to see if it works properly now-123.";
        ProjectDescription desc1 = new ProjectDescription(validDescription);
        ProjectDescription desc2 = new ProjectDescription(validDescription);

        assertEquals(desc1.hashCode(), desc2.hashCode());
    }

    @Test 
    @DisplayName("Should return invalid characters error message")
    void shouldReturnInvalidCharactersErrorMessage() {
        String expectedMessage = "Project description can only contain letters, numbers, spaces and hyphens";
        ProjectDescription description = new ProjectDescription("This is a description of one hundred characters we need to test the limit of the description to see if it works properly now-123.");
        assertEquals(expectedMessage, description.getInvalidCharactersErrorMessage());
    }
}