package com.example.EasyRoom.use_case;

import com.example.EasyRoom.model.project.Project;
import com.example.EasyRoom.model.project.ProjectRepository;
import com.example.EasyRoom.model.project.ProjectValidator;

public class CreateProject {

    private final ProjectRepository projectRepository;
    private final ProjectValidator projectValidator;

    public CreateProject(ProjectRepository projectRepository, ProjectValidator projectValidator) {
        this.projectRepository = projectRepository;
        this.projectValidator = projectValidator;
    }

    public Project execute(String projectName) {
        // 1. Setup (Arrange): prepare les données et effectue les validations initiale
        projectValidator.validateProjectName(projectName);

        // 2. Exercise Exécute l'action principale
        Project project = new Project(projectName);
        Project savedProject = projectRepository.save(project);

        // 3. Verify (Assert): verif les résultat et gere les effets secondaires
        if (savedProject.getId() == null) {
            throw new IllegalStateException("Failed to save project: ID is null");
        }

        return savedProject;
    }
}