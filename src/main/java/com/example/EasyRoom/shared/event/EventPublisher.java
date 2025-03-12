package com.example.EasyRoom.shared.event;

public interface EventPublisher {
    void publish(DomainEvent event);
}