package com.example.EasyRoom.model.project;

import java.util.Objects;

public  class ProjectName {
    private final String value;

    public ProjectName(String value) {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException("Project name cannot be empty");
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
        ProjectName that = (ProjectName) o;
        return Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}