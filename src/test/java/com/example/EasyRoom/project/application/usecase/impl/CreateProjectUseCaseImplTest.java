package com.example.EasyRoom.project.application.usecase.impl;

import com.example.EasyRoom.project.application.dto.ProjectRequestDto;
import com.example.EasyRoom.project.application.dto.ProjectResponseDto;
import com.example.EasyRoom.project.domain.aggregate.ProjectAggregate;
import com.example.EasyRoom.project.domain.repository.ProjectRepository;
import com.example.EasyRoom.project.domain.service.ProjectDomainService;
import com.example.EasyRoom.project.domain.valueobject.ProjectName;
import com.example.EasyRoom.project.domain.event.ProjectCreatedEvent;
import com.example.EasyRoom.shared.event.EventPublisher;
import com.example.EasyRoom.shared.exception.DomainException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CreateProjectUseCaseImplTest {

    @Mock
    private ProjectRepository projectRepository;

    @Mock
    private ProjectDomainService projectDomainService;

    @Mock
    private EventPublisher eventPublisher;

    @InjectMocks
    private CreateProjectUseCaseImpl createProjectUseCase;

    private ProjectRequestDto requestDto;

    @BeforeEach
    void setUp() {
        // Setup global pour tous les tests (partie du Sandwich Pattern)
        requestDto = new ProjectRequestDto();
        requestDto.setName("Test Project");
    }

    @Test
    void execute_shouldCreateProjectSuccessfully() {
        // 1. Setup (Arrange) : Préparer les données et configurer les mocks
        ProjectAggregate savedProject = new ProjectAggregate(new ProjectName("Test Project"));
        savedProject.setId(1L);

        when(projectRepository.findByName(any(ProjectName.class))).thenReturn(null); // Projet n'existe pas
        when(projectRepository.save(any(ProjectAggregate.class))).thenReturn(savedProject);

        // 2. Exercise (Act) : Exécuter l'action à tester
        ProjectResponseDto response = createProjectUseCase.execute(requestDto);

        // 3. Verify (Assert) : Vérifier les résultats attendus
        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertEquals("Test Project", response.getName());
        assertTrue(response.getRooms().isEmpty());
        verify(projectDomainService).validateProjectName("Test Project");
        verify(projectRepository).findByName(any(ProjectName.class));
        verify(projectRepository).save(any(ProjectAggregate.class));
        verify(eventPublisher).publish(eq(new ProjectCreatedEvent(1L)));

        // Teardown implicite : Mockito nettoie automatiquement les mocks
    }

    @Test
    void execute_shouldThrowExceptionWhenProjectNameAlreadyExists() {
        // 1. Setup (Arrange)
        ProjectAggregate existingProject = new ProjectAggregate(new ProjectName("Test Project"));
        when(projectRepository.findByName(any(ProjectName.class))).thenReturn(existingProject);

        // 2. Exercise (Act) & Verify (Assert)
        DomainException exception = assertThrows(DomainException.class, () -> createProjectUseCase.execute(requestDto));
        assertEquals("Failed to create project: Project with this name already exists", exception.getMessage());
        verify(projectDomainService).validateProjectName("Test Project");
        verify(projectRepository).findByName(any(ProjectName.class));
        verify(projectRepository, never()).save(any(ProjectAggregate.class));
        verify(eventPublisher, never()).publish(any());

        // Teardown implicite
    }

    @Test
    void execute_shouldThrowExceptionWhenProjectNameValidationFails() {
        // 1. Setup (Arrange)
        doThrow(new IllegalArgumentException("Project name cannot be null or empty")).when(projectDomainService).validateProjectName("Test Project");

        // 2. Exercise (Act) & Verify (Assert)
        DomainException exception = assertThrows(DomainException.class, () -> createProjectUseCase.execute(requestDto));
        assertEquals("Failed to create project: Project name cannot be null or empty", exception.getMessage());
        verify(projectDomainService).validateProjectName("Test Project");
        verify(projectRepository, never()).findByName(any(ProjectName.class));
        verify(projectRepository, never()).save(any(ProjectAggregate.class));
        verify(eventPublisher, never()).publish(any());

        // Teardown implicite
    }
}