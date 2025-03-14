package com.example.EasyRoom.application.project.dto;

import java.util.List;

public class ProjectResponse {
    private final String id;
    private final String name;
    private final String description;
    private final String userId;

    public ProjectResponse(String id, String name, String description, String userId, List<String> roomIds) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.userId = userId;
    }

    // Getters
    public String getId() { return id; }
    public String getName() { return name; }
    public String getDescription() { return description; }
    public String getUserId() { return userId; }
}