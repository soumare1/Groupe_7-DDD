package com.example.EasyRoom.model.project;

import java.util.ArrayList;
import java.util.List;

public class Project {

    private Long id;
    private String name;
    private List<Room> rooms;

    public Project(String name) {
        this.name = name;
        this.rooms = new ArrayList<>();
    }

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

    public List<Room> getRooms() {
        return rooms;
    }

    public void addRoom(Room room) {
        this.rooms.add(room);
    }
}