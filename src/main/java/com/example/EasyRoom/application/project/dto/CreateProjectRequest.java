package com.example.EasyRoom.application.project.dto;

public class CreateProjectRequest {
    private final String name;
    private final String description;
    private final String userId;

    public CreateProjectRequest(String name, String description, String userId) {
        this.name = name;
        this.description = description;
        this.userId = userId;
    }

    public String getName() { return name; }
    public String getDescription() { return description; }
    public String getUserId() { return userId; }
}