package com.example.EasyRoom.project.application.usecase;

import com.example.EasyRoom.project.application.dto.ProjectResponseDto;

public interface RemoveRoomFromProjectUseCase {
    ProjectResponseDto execute(Long projectId, Long roomId);
}