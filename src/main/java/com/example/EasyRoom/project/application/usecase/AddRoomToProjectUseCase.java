package com.example.EasyRoom.project.application.usecase;

import com.example.EasyRoom.project.application.dto.RoomRequestDto;
import com.example.EasyRoom.project.application.dto.ProjectResponseDto;

public interface AddRoomToProjectUseCase {
    ProjectResponseDto execute(Long projectId, RoomRequestDto roomRequest);
}