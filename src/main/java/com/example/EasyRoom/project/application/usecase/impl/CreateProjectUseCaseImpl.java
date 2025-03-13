package com.example.EasyRoom.project.application.usecase.impl;

import com.example.EasyRoom.project.application.dto.ProjectRequestDto;
import com.example.EasyRoom.project.application.dto.ProjectResponseDto;
import com.example.EasyRoom.project.application.usecase.CreateProjectUseCase;
import com.example.EasyRoom.project.domain.aggregate.ProjectAggregate;
import com.example.EasyRoom.project.domain.repository.ProjectRepository;
import com.example.EasyRoom.project.domain.service.ProjectDomainService;
import com.example.EasyRoom.project.domain.valueobject.ProjectName;
import com.example.EasyRoom.project.domain.event.ProjectCreatedEvent;
import com.example.EasyRoom.shared.event.EventPublisher;
import com.example.EasyRoom.shared.exception.DomainException;

import java.util.Collections;

public class CreateProjectUseCaseImpl implements CreateProjectUseCase {

    private final ProjectRepository projectRepository;
    private final ProjectDomainService projectDomainService;
    private final EventPublisher eventPublisher;

    public CreateProjectUseCaseImpl(ProjectRepository projectRepository, ProjectDomainService projectDomainService, EventPublisher eventPublisher) {
        this.projectRepository = projectRepository;
        this.projectDomainService = projectDomainService;
        this.eventPublisher = eventPublisher;
    }

    @Override
    public ProjectResponseDto execute(ProjectRequestDto request) {
        try {
            // Validation des données d'entrée
            projectDomainService.validateProjectName(request.getName());

            // Vérifier si un projet avec le même nom existe déjà
            if (projectRepository.findByName(new ProjectName(request.getName())) != null) {
                throw new IllegalArgumentException("Project with this name already exists");
            }

            // Créer l'agrégat
            ProjectAggregate project = new ProjectAggregate(new ProjectName(request.getName()));
            ProjectAggregate savedProject = projectRepository.save(project);

            // Publier un événement
            eventPublisher.publish(new ProjectCreatedEvent(savedProject.getId()));

            // Retourner la réponse
            return new ProjectResponseDto(savedProject.getId(), savedProject.getName().getValue(), Collections.emptyList());
        } catch (IllegalArgumentException e) {
            throw new DomainException("Failed to create project: " + e.getMessage(), e);
        }
    }
}