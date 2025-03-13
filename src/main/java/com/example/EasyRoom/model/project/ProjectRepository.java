package com.example.EasyRoom.model.project;

public interface ProjectRepository {
    Project findById(Long id);
    Project save(Project project);
}