package com.example.EasyRoom.project.application.dto;

import java.util.List;

public class ProjectResponseDto {
    private Long id;
    private String name;
    private List<RoomResponseDto> rooms;

    public ProjectResponseDto(Long id, String name, List<RoomResponseDto> rooms) {
        this.id = id;
        this.name = name;
        this.rooms = rooms;
    }

    // Getters et setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<RoomResponseDto> getRooms() {
        return rooms;
    }

    public void setRooms(List<RoomResponseDto> rooms) {
        this.rooms = rooms;
    }
}