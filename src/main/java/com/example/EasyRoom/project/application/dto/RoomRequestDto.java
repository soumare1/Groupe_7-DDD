package com.example.EasyRoom.project.application.dto;

import jakarta.validation.constraints.NotNull;

public class RoomRequestDto {
    @NotNull(message = "Room name cannot be null")
    private String name;

    @NotNull(message = "Capacity cannot be null")
    private Integer capacity;

    // Getters et setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }
}