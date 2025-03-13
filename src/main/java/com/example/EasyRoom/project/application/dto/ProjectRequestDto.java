package com.example.EasyRoom.project.application.dto;

import jakarta.validation.constraints.NotNull;

public class ProjectRequestDto {
    @NotNull(message = "Project name cannot be null")
    private String name;

    // Getters et setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}