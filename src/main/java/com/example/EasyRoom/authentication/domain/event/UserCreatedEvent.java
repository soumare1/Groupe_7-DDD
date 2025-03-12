package com.example.EasyRoom.authentication.domain.event;

import com.example.EasyRoom.shared.event.DomainEvent;

public class UserCreatedEvent implements DomainEvent {
    private final Long userId;
    private final String email;

    public UserCreatedEvent(Long userId, String email) {
        this.userId = userId;
        this.email = email;
    }

    public Long getUserId() {
        return userId;
    }

    public String getEmail() {
        return email;
    }
}