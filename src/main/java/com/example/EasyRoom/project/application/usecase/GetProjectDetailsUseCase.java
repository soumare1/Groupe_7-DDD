package com.example.EasyRoom.project.application.usecase;

import com.example.EasyRoom.project.application.dto.ProjectResponseDto;

public interface GetProjectDetailsUseCase {
    ProjectResponseDto execute(Long projectId);
}