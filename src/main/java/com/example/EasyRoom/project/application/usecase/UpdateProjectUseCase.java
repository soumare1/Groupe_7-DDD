package com.example.EasyRoom.project.application.usecase;

import com.example.EasyRoom.project.application.dto.ProjectRequestDto;
import com.example.EasyRoom.project.application.dto.ProjectResponseDto;

public interface UpdateProjectUseCase {
    ProjectResponseDto execute(Long projectId, ProjectRequestDto request);
}