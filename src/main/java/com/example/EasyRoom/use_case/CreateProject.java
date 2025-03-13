package com.example.EasyRoom.use_case;

import com.example.EasyRoom.project.application.dto.ProjectRequestDto;
import com.example.EasyRoom.project.application.dto.ProjectResponseDto;
import com.example.EasyRoom.project.domain.aggregate.ProjectAggregate;
import com.example.EasyRoom.project.domain.repository.ProjectRepository;
import com.example.EasyRoom.project.domain.service.ProjectDomainService;
import com.example.EasyRoom.project.domain.valueobject.ProjectName;
import com.example.EasyRoom.shared.exception.DomainException;

import java.util.Collections;

public class CreateProject {

    private final ProjectRepository projectRepository;
    private final ProjectDomainService projectDomainService;

    public CreateProject(ProjectRepository projectRepository, ProjectDomainService projectDomainService) {
        this.projectRepository = projectRepository;
        this.projectDomainService = projectDomainService;
    }

    public ProjectResponseDto execute(ProjectRequestDto request) {
        // 1. Setup (Arrange): Préparer les données et effectuer les validations initiales
        if (request == null || request.getName() == null) {
            throw new IllegalArgumentException("Request or project name cannot be null");
        }

        projectDomainService.validateProjectName(request.getName());
        ProjectName projectName = new ProjectName(request.getName());

        // 2. Exercise (Act): Exécuter l'action principale
        ProjectAggregate project = new ProjectAggregate(projectName);
        ProjectAggregate savedProject = projectRepository.save(project);

        // 3. Verify (Assert): Vérifier les résultats et gérer les effets secondaires
        if (savedProject.getId() == null) {
            throw new IllegalStateException("Failed to save project: ID is null");
        }

        return new ProjectResponseDto(savedProject.getId(), savedProject.getName().getValue(), Collections.emptyList());
    }
}