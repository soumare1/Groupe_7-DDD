package com.example.EasyRoom.model.project;

import java.util.Objects;

public final class Project {
    private ProjectId id; //equals and hashcode
    private ProjectName name;
    private ProjectDescription ProjectDescription;
    private UserId userId;

    private Project(ProjectId id ,ProjectName name, ProjectDescription ProjectDescription, String userId) {
        this.id = id;
        this.name = name;
        this.ProjectDescription = ProjectDescription;
        this.userId = new UserId(userId);
    }

    public static Project create(String name, String ProjectDescription, String userId) {
        return new Project(
            ProjectId.generate(),
            new ProjectName(name),
            new ProjectDescription(ProjectDescription),
            userId
        );
    }

    public static Project reconstitute(String id, String name, String ProjectDescription, String userId) {
        return new Project(
            new ProjectId(id),
            new ProjectName(name),
            new ProjectDescription(ProjectDescription),
            userId
        );
    }

    public String getId() {
        return id.getValue();
    }

    public ProjectName getName() {
        return name;
    }

    public ProjectDescription getProjectDescription() {
        return ProjectDescription;
    }

    public UserId getUserId() {
        return userId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Project project = (Project) o;
        return Objects.equals(id.getValue(), project.id.getValue());
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id.getValue()
        );
    }
}