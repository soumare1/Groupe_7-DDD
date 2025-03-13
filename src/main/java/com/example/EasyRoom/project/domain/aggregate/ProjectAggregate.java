package com.example.EasyRoom.project.domain.aggregate;

import com.example.EasyRoom.project.domain.valueobject.ProjectName;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ProjectAggregate {
    private Long id;
    private ProjectName name;
    private List<Room> rooms;

    public ProjectAggregate(ProjectName name) {
        this.name = name;
        this.rooms = new ArrayList<>();
    }

    public void addRoom(Room room) {
        this.rooms.add(room);
    }

    public void removeRoom(Room room) {
        this.rooms.remove(room);
    }

    // Getters et setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ProjectName getName() {
        return name;
    }

    public void setName(ProjectName name) {
        this.name = name;
    }

    public List<Room> getRooms() {
        return rooms;
    }

    public void setRooms(List<Room> rooms) {
        this.rooms = rooms;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProjectAggregate that = (ProjectAggregate) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(name, that.name) &&
                Objects.equals(rooms, that.rooms);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, rooms);
    }
}