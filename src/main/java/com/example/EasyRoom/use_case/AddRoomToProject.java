package com.example.EasyRoom.use_case;

import com.example.EasyRoom.model.project.Project;
import com.example.EasyRoom.model.project.ProjectRepository;
import com.example.EasyRoom.model.project.Room;
import com.example.EasyRoom.model.project.RoomRepository;
import com.example.EasyRoom.model.project.ProjectValidator;

public class AddRoomToProject {

    private final ProjectRepository projectRepository;
    private final RoomRepository roomRepository;
    private final ProjectValidator projectValidator;

    public AddRoomToProject(ProjectRepository projectRepository, RoomRepository roomRepository, ProjectValidator projectValidator) {
        this.projectRepository = projectRepository;
        this.roomRepository = roomRepository;
        this.projectValidator = projectValidator;
    }

    public Project execute(Long projectId, String roomName, int capacity) {
        // 1. Setup (Arrange): Préparer les données et effectuer les validations initiales
        if (projectId == null) {
            throw new IllegalArgumentException("Project ID cannot be null");
        }

        projectValidator.validateRoomName(roomName);
        projectValidator.validateCapacity(capacity);

        Project project = projectRepository.findById(projectId);
        if (project == null) {
            throw new ProjectNotFoundException();
        }

        // 2. Exercise (Act): Exécuter l'action principale
        Room room = new Room(roomName, capacity);
        Room savedRoom = roomRepository.save(room);
        project.addRoom(savedRoom);
        Project updatedProject = projectRepository.save(project);

        // 3. Verify (Assert): Vérifier les résultats et gérer les effets secondaires
        if (!updatedProject.getRooms().contains(savedRoom)) {
            throw new IllegalStateException("Failed to add room to project");
        }

        return updatedProject;
    }
}