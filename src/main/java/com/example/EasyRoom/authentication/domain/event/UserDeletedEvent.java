package com.example.EasyRoom.authentication.domain.event;

import com.example.EasyRoom.shared.event.DomainEvent;

import java.util.Objects;

public class UserDeletedEvent implements DomainEvent {
    private final Long userId;

    public UserDeletedEvent(Long userId) {
        this.userId = userId;
    }

    public Long getUserId() {
        return userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserDeletedEvent that = (UserDeletedEvent) o;
        return Objects.equals(userId, that.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId);
    }
}