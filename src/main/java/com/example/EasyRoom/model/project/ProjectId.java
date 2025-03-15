package com.example.EasyRoom.model.project;

import java.util.Objects;
import java.util.UUID;

public  final class ProjectId {
    private final String value;

    public ProjectId(String value) {
        if (value == null || value.trim().isEmpty()) {
            throw new ValidateContainException("Project ID cannot be empty");
        }
        this.value = value;
    }

    public static ProjectId generate() {
        return new ProjectId(UUID.randomUUID().toString());
    }

    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProjectId projectId = (ProjectId) o;
        return Objects.equals(value, projectId.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
