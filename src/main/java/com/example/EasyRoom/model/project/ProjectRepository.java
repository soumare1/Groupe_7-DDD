package com.example.EasyRoom.model.project;
import java.util.List;

public interface ProjectRepository {
    Project save(Project project);
    Project findById(ProjectId id);
    List<Project> findAll();
    void delete(Project project);
    Project findByUserId(String userId);
    Project findByName(String name);
}
