package com.example.EasyRoom.infrastructure.persistence;

import com.example.EasyRoom.model.project.Project;
import com.example.EasyRoom.model.project.ProjectId;
import com.example.EasyRoom.model.project.ProjectRepository;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProjectRepositoryImpl implements ProjectRepository {
    
    private Map<Object, Project> projects = new HashMap<>();

    @Override
    public Project save(Project project) {
        projects.put(new ProjectId(project.getId()), project);
        return project;
    }

    @Override
    public Project findById(ProjectId id) {
        return projects.get(id);
    }


    @Override
    public void delete(Project project) {
        projects.remove(new ProjectId(project.getId()));
    }

    @Override
    public Project findByUserId(String userId) {
        return projects.values().stream()
                .filter(project -> project.getUserId().equals(userId))
                .findFirst()
                .orElse(null);
    }

    @Override
    public Project findByName(String name) {
        return projects.values().stream()
                .filter(project -> project.getName().getValue().equals(name))
                .findFirst()
                .orElse(null);
    }

    // Removed duplicate method 'findById'

    @Override
    public List<Project> findAll() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findAll'");
    }

}