package com.example.EasyRoom.test.use_case;

import com.example.EasyRoom.model.project.Project;
import com.example.EasyRoom.model.project.ProjectRepository;
import com.example.EasyRoom.model.project.ProjectValidator;
import com.example.EasyRoom.use_case.CreateProject;
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
class CreateProjectShould {

    @Mock
    private ProjectRepository projectRepository;

    @Mock
    private ProjectValidator projectValidator;

    @InjectMocks
    private CreateProject createProject;

    @BeforeEach
    void setUp() {
        // Setup global pour tous les tests
    }

    @Test
    void createProjectSuccessfully() {
        // 1. Setup (Arrange): Préparer les données et configurer les mocks
        String projectName = "Test Project";
        Project mockProject = new Project(projectName);
        mockProject.setId(1L);
        doNothing().when(projectValidator).validateProjectName(projectName); // Correction ici
        when(projectRepository.save(any(Project.class))).thenReturn(mockProject);

        // 2. Exercise (Act): Exécuter l'action à tester
        Project result = createProject.execute(projectName);

        // 3. Verify (Assert): Vérifier les résultats attendus
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Test Project", result.getName());
        verify(projectValidator).validateProjectName(projectName);
        verify(projectRepository).save(any(Project.class));
    }

    @Test
    void throwIllegalArgumentExceptionWhenProjectNameIsNull() {
        // 1. Setup (Arrange): Préparer les données et configurer les mocks
        doThrow(new IllegalArgumentException("Project name cannot be null or empty")).when(projectValidator).validateProjectName(null);

        // 2. Exercise (Act) & 3. Verify (Assert): Exécuter et vérifier l'exception
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> createProject.execute(null));
        assertEquals("Project name cannot be null or empty", exception.getMessage());
        verify(projectValidator).validateProjectName(null);
        verify(projectRepository, never()).save(any(Project.class));
    }

    @Test
    void throwIllegalStateExceptionWhenSaveFails() {
        // 1. Setup (Arrange): Préparer les données et configurer les mocks
        String projectName = "Test Project";
        doNothing().when(projectValidator).validateProjectName(projectName); // Correction ici
        when(projectRepository.save(any(Project.class))).thenReturn(new Project(projectName)); // ID null

        // 2. Exercise (Act) & 3. Verify (Assert): Exécuter et vérifier l'exception
        IllegalStateException exception = assertThrows(IllegalStateException.class, () -> createProject.execute(projectName));
        assertEquals("Failed to save project: ID is null", exception.getMessage());
        verify(projectValidator).validateProjectName(projectName);
        verify(projectRepository).save(any(Project.class));
    }
}