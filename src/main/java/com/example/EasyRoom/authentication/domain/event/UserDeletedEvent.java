package com.example.EasyRoom.authentication.domain.event;

import com.example.EasyRoom.shared.event.DomainEvent;

public class UserDeletedEvent implements DomainEvent {
    private final Long userId;

    public UserDeletedEvent(Long userId) {
        this.userId = userId;
    }

    public Long getUserId() {
        return userId;
    }
}