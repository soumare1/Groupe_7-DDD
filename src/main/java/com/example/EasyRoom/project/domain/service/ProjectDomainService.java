package com.example.EasyRoom.project.domain.service;

public class ProjectDomainService {

    public void validateProjectName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Project name cannot be null or empty");
        }
        if (name.length() > 100) {
            throw new IllegalArgumentException("Project name cannot exceed 100 characters");
        }
    }
}