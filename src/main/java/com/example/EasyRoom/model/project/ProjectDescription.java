package com.example.EasyRoom.model.project;

import java.util.Objects;

public  final class ProjectDescription {
    private final String value;

    public ProjectDescription(String value) {
        if (value == null) {
            throw new IllegalArgumentException("Description cannot be null");
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
        ProjectDescription that = (ProjectDescription) o;  
        return Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}