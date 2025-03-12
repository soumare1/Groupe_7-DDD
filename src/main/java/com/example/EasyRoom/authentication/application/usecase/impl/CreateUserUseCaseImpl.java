package com.example.EasyRoom.authentication.application.usecase.impl;

import com.example.EasyRoom.authentication.application.dto.UserRequestDto;
import com.example.EasyRoom.authentication.application.dto.UserResponseDto;
import com.example.EasyRoom.authentication.application.usecase.CreateUserUseCase;
import com.example.EasyRoom.authentication.domain.aggregate.UserAggregate;
import com.example.EasyRoom.authentication.domain.repository.UserRepository;
import com.example.EasyRoom.authentication.domain.service.UserDomainService;
import com.example.EasyRoom.authentication.domain.event.UserCreatedEvent;
import com.example.EasyRoom.authentication.domain.valueobject.Email;
import com.example.EasyRoom.authentication.domain.valueobject.Password;
import com.example.EasyRoom.shared.event.EventPublisher;
import com.example.EasyRoom.shared.exception.DomainException;

public class CreateUserUseCaseImpl implements CreateUserUseCase {

    private final UserRepository userRepository;
    private final UserDomainService userDomainService;
    private final EventPublisher eventPublisher;

    public CreateUserUseCaseImpl(UserRepository userRepository, UserDomainService userDomainService, EventPublisher eventPublisher) {
        this.userRepository = userRepository;
        this.userDomainService = userDomainService;
        this.eventPublisher = eventPublisher;
    }

    @Override
    public UserResponseDto execute(UserRequestDto request) {
        try {
            userDomainService.validateEmail(request.getEmail());
            userDomainService.validatePassword(request.getPassword());

            Email email = new Email(request.getEmail());
            Password password = new Password(request.getPassword());

            UserAggregate existingUser = userRepository.findByEmail(email);
            if (existingUser != null) {
                throw new IllegalArgumentException("Email already exists");
            }

            UserAggregate userAggregate = new UserAggregate(email, password);
            UserAggregate savedUser = userRepository.save(userAggregate);

            eventPublisher.publish(new UserCreatedEvent(savedUser.getId(), savedUser.getEmail().getValue()));

            return new UserResponseDto(savedUser.getId(), savedUser.getEmail().getValue());
        } catch (IllegalArgumentException e) {
            throw new DomainException("Failed to create user: " + e.getMessage(), e);
        }
    }
}