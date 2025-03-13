package com.example.EasyRoom.project.domain.aggregate;

import com.example.EasyRoom.project.domain.valueobject.RoomName;
import com.example.EasyRoom.project.domain.valueobject.Capacity;

import java.util.Objects;

public class Room {
    private Long id;
    private RoomName name;
    private Capacity capacity;

    public Room(RoomName name, Capacity capacity) {
        this.name = name;
        this.capacity = capacity;
    }

    // Getters et setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public RoomName getName() {
        return name;
    }

    public void setName(RoomName name) {
        this.name = name;
    }

    public Capacity getCapacity() {
        return capacity;
    }

    public void setCapacity(Capacity capacity) {
        this.capacity = capacity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Room room = (Room) o;
        return Objects.equals(id, room.id) &&
                Objects.equals(name, room.name) &&
                Objects.equals(capacity, room.capacity);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, capacity);
    }
}