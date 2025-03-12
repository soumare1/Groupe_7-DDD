package com.example.EasyRoom.authentication.application.usecase.impl;

import com.example.EasyRoom.authentication.application.usecase.DeleteAccountUseCase;
import com.example.EasyRoom.authentication.domain.aggregate.UserAggregate;
import com.example.EasyRoom.authentication.domain.repository.UserRepository;
import com.example.EasyRoom.authentication.domain.event.UserDeletedEvent;
import com.example.EasyRoom.shared.event.EventPublisher;
import com.example.EasyRoom.shared.exception.DomainException;

public class DeleteAccountUseCaseImpl implements DeleteAccountUseCase {

    private final UserRepository userRepository;
    private final EventPublisher eventPublisher;

    public DeleteAccountUseCaseImpl(UserRepository userRepository, EventPublisher eventPublisher) {
        this.userRepository = userRepository;
        this.eventPublisher = eventPublisher;
    }

    @Override
    public void execute(Long userId) {
        try {
            if (userId == null) {
                throw new IllegalArgumentException("User ID cannot be null");
            }

            UserAggregate userAggregate = userRepository.findById(userId);
            if (userAggregate == null) {
                throw new IllegalArgumentException("User not found");
            }

            userRepository.delete(userAggregate);
            eventPublisher.publish(new UserDeletedEvent(userId));
        } catch (IllegalArgumentException e) {
            throw new DomainException("Failed to delete account: " + e.getMessage(), e);
        }
    }
}