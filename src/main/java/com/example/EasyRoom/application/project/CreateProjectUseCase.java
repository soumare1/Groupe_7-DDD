package com.example.EasyRoom.application.project;

import com.example.EasyRoom.model.project.Project;
import com.example.EasyRoom.model.project.ProjectRepository;
import java.util.List;

import com.example.EasyRoom.application.project.dto.*;

public class CreateProjectUseCase {
    private final ProjectRepository projectRepository;

    public CreateProjectUseCase(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    public ProjectResponse execute(CreateProjectRequest request) {
        Project project = Project.create(
            request.getName(),
            request.getDescription(),
            new String(request.getUserId())
        );

        Project savedProject = projectRepository.save(project);

        return new ProjectResponse(
            savedProject.getId().toString(),
            savedProject.getName().getValue(),
            savedProject.getProjectDescription().getValue(),
            savedProject.getUserId().getValue().toString(),
            List.of() 
        );
    }
}