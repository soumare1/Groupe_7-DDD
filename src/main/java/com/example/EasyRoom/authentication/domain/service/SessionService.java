package com.example.EasyRoom.authentication.domain.service;

public interface SessionService {
    void invalidateSession(Long userId);
}