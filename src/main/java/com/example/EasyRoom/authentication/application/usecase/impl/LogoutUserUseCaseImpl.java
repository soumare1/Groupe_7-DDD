package com.example.EasyRoom.authentication.application.usecase.impl;

import com.example.EasyRoom.authentication.application.dto.UserResponseDto;
import com.example.EasyRoom.authentication.application.usecase.LogoutUserUseCase;
import com.example.EasyRoom.authentication.domain.service.SessionService;
import com.example.EasyRoom.shared.exception.DomainException;

public class LogoutUserUseCaseImpl implements LogoutUserUseCase {

    private final SessionService sessionService;

    public LogoutUserUseCaseImpl(SessionService sessionService) {
        this.sessionService = sessionService;
    }

    @Override
    public UserResponseDto execute(Long userId) {
        try {
            if (userId == null) {
                throw new IllegalArgumentException("User ID cannot be null");
            }

            sessionService.invalidateSession(userId);
            return new UserResponseDto(userId, null);
        } catch (IllegalArgumentException e) {
            throw new DomainException("Logout failed: " + e.getMessage(), e);
        }
    }
}