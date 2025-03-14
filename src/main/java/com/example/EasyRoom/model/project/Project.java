package com.example.EasyRoom.model.project;

import java.rmi.server.UID;
import java.util.Objects;
import java.util.UUID;

public final class Project {
    private ProjectId id; //equals and hashcode
    private ProjectName name;
    private Description description;
    private UserId userId;

    private Project(ProjectId id ,ProjectName name, Description description, String userId) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.userId = new UserId(userId);
    }

    public static Project create(String name, String description, String userId) {
        return new Project(
            ProjectId.generate(),
            new ProjectName(name),
            new Description(description),
            userId
        );
    }

    public static Project reconstitute(String id, String name, String description, String userId) {
        return new Project(
            new ProjectId(id),
            new ProjectName(name),
            new Description(description),
            userId
        );
    }

    // Getters - No setters to ensure immutability
    public String getId() {
        return id.getValue();
    }

    public ProjectName getName() {
        return name;
    }

    public Description getDescription() {
        return description;
    }

    public UserId getUserId() {
        return userId;
    }


    // Value Objects , need to be immutable
     public static final class ProjectId {
        private final String value;

        public ProjectId(String value) {
            if (value == null || value.trim().isEmpty()) {
                throw new IllegalArgumentException("Project ID cannot be empty");
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

    public static final class UserId {
        private final String value;

        public UserId(String value) {
            if (value == null || value.trim().isEmpty()) {
                throw new IllegalArgumentException("User ID cannot be empty");
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
            UserId userId = (UserId) o;
            return Objects.equals(value, userId.value);
        }

        @Override
        public int hashCode() {
            return Objects.hash(value);
        }
    }

    public static class ProjectName {
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

    public static final class Description {
        private final String value;
    
        public Description(String value) {
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
            Description that = (Description) o;  
            return Objects.equals(value, that.value);
        }
    
        @Override
        public int hashCode() {
            return Objects.hash(value);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Project project = (Project) o;
        return Objects.equals(id.getValue(), project.id.getValue()) &&
               Objects.equals(name.getValue(), project.name.getValue()) &&
               Objects.equals(description.getValue(), project.description.getValue()) &&
               Objects.equals(userId.getValue(), project.userId.getValue());
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id.getValue(), 
            name.getValue(), 
            description.getValue(), 
            userId.getValue()
        );
    }
}