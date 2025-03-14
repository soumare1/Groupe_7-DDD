package com.example.EasyRoom.appication.project;

import com.example.EasyRoom.model.project.Project;
import com.example.EasyRoom.model.project.ProjectRepository;
import com.example.EasyRoom.application.project.CreateProjectUseCase;
import com.example.EasyRoom.application.project.dto.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class CreateProjectUseCaseTest {
    
    @Mock
    private ProjectRepository projectRepository;
    
    private CreateProjectUseCase useCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        useCase = new CreateProjectUseCase(projectRepository);
    }

    @Test
    @DisplayName("Should create project through use case")
    void shouldCreateProject() {
        // Arrange
        String name = "Test Project";
        String description = "Test Description";
        String userId = "user123";
        
        CreateProjectRequest request = new CreateProjectRequest(name, description, userId);
        
        Project project = Project.create(name, description, userId);
        when(projectRepository.save(any(Project.class))).thenReturn(project);

        // Act
        ProjectResponse response = useCase.execute(request);

        // Assert
        assertNotNull(response);
        assertEquals(name, response.getName());
        assertEquals(description, response.getDescription());
        assertEquals(userId, response.getUserId());
        verify(projectRepository).save(any(Project.class));
    }

    @Test
    @DisplayName("Should throw exception when creating project with invalid data")
    void shouldThrowExceptionOnInvalidData() {
        // Arrange
        CreateProjectRequest request = new CreateProjectRequest("", "Description", "user123");

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> {
            useCase.execute(request);
        });
        
        verify(projectRepository, never()).save(any(Project.class));
    }

    @Test
    @DisplayName("Should throw exception when repository fails")
    void shouldThrowExceptionOnRepositoryFailure() {
        // Arrange
        CreateProjectRequest request = new CreateProjectRequest(
            "Test Project", 
            "Description", 
            "user123"
        );
        
        when(projectRepository.save(any(Project.class)))
            .thenThrow(new RuntimeException("Repository error"));

        // Act & Assert
        assertThrows(RuntimeException.class, () -> {
            useCase.execute(request);
        });
    }

    
}