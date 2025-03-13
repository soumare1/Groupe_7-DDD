package com.example.EasyRoom.project.domain.repository;

import com.example.EasyRoom.project.domain.aggregate.ProjectAggregate;
import com.example.EasyRoom.project.domain.valueobject.ProjectName;

public interface ProjectRepository {
    ProjectAggregate findByName(ProjectName name);
    ProjectAggregate save(ProjectAggregate project);
    ProjectAggregate findById(Long id);
    void delete(ProjectAggregate project);
}