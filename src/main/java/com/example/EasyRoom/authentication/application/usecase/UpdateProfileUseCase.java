package com.example.EasyRoom.authentication.application.usecase;

import com.example.EasyRoom.authentication.application.dto.UserRequestDto;
import com.example.EasyRoom.authentication.application.dto.UserResponseDto;

public interface UpdateProfileUseCase {
    UserResponseDto execute(Long userId, UserRequestDto request);
}