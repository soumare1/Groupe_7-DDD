package com.example.EasyRoom.project.domain.event;

import com.example.EasyRoom.shared.event.DomainEvent;

public record ProjectCreatedEvent(Long projectId) implements DomainEvent {
}