package com.example.EasyRoom.project.domain.valueobject;

import java.util.Objects;

public class RoomName {
    private final String value;

    public RoomName(String value) {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException("Room name cannot be null or empty");
        }
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RoomName that = (RoomName) o;
        return Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}