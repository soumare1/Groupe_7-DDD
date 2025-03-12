package com.example.EasyRoom.authentication.application.usecase;

import com.example.EasyRoom.authentication.application.dto.UserResponseDto;

public interface LogoutUserUseCase {
    UserResponseDto execute(Long userId);
}