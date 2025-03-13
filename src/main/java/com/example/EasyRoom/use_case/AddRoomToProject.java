package com.example.EasyRoom.use_case;

import com.example.EasyRoom.project.application.dto.ProjectResponseDto;
import com.example.EasyRoom.project.application.dto.RoomRequestDto;
import com.example.EasyRoom.project.application.dto.RoomResponseDto;
import com.example.EasyRoom.project.domain.aggregate.ProjectAggregate;
import com.example.EasyRoom.project.domain.aggregate.Room;
import com.example.EasyRoom.project.domain.repository.ProjectRepository;
import com.example.EasyRoom.project.domain.repository.RoomRepository;
import com.example.EasyRoom.project.domain.service.ProjectDomainService;
import com.example.EasyRoom.project.domain.valueobject.Capacity;
import com.example.EasyRoom.project.domain.valueobject.RoomName;
import com.example.EasyRoom.shared.exception.DomainException;

import java.util.stream.Collectors;

public class AddRoomToProject {

    private final ProjectRepository projectRepository;
    private final RoomRepository roomRepository;
    private final ProjectDomainService projectDomainService;

    public AddRoomToProject(ProjectRepository projectRepository, RoomRepository roomRepository, ProjectDomainService projectDomainService) {
        this.projectRepository = projectRepository;
        this.roomRepository = roomRepository;
        this.projectDomainService = projectDomainService;
    }

    public ProjectResponseDto execute(Long projectId, RoomRequestDto request) {
        // 1. Setup (Arrange): Préparer les données et effectuer les validations initiales
        if (projectId == null) {
            throw new IllegalArgumentException("Project ID cannot be null");
        }
        if (request == null || request.getName() == null || request.getCapacity() <= 0) {
            throw new IllegalArgumentException("Room request, name, or capacity cannot be null or invalid");
        }

        projectDomainService.validateRoomName(request.getName());
        projectDomainService.validateCapacity(request.getCapacity());

        ProjectAggregate project = projectRepository.findById(projectId);
        if (project == null) {
            throw new DomainException("Project not found");
        }

        // 2. Exercise (Act): Exécuter l'action principale
        Room room = new Room(new RoomName(request.getName()), new Capacity(request.getCapacity()));
        Room savedRoom = roomRepository.save(room);
        project.addRoom(savedRoom);
        ProjectAggregate updatedProject = projectRepository.save(project);

        // 3. Verify (Assert): Vérifier les résultats et gérer les effets secondaires
        if (!updatedProject.getRooms().contains(savedRoom)) {
            throw new IllegalStateException("Failed to add room to project");
        }

        return new ProjectResponseDto(
                updatedProject.getId(),
                updatedProject.getName().getValue(),
                updatedProject.getRooms().stream()
                        .map(r -> new RoomResponseDto(r.getId(), r.getName().getValue(), r.getCapacity().getValue()))
                        .collect(Collectors.toList())
        );
    }
}