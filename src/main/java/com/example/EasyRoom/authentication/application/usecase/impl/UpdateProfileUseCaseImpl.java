package com.example.EasyRoom.authentication.application.usecase.impl;

import com.example.EasyRoom.authentication.application.dto.UserRequestDto;
import com.example.EasyRoom.authentication.application.dto.UserResponseDto;
import com.example.EasyRoom.authentication.application.usecase.UpdateProfileUseCase;
import com.example.EasyRoom.authentication.domain.aggregate.UserAggregate;
import com.example.EasyRoom.authentication.domain.repository.UserRepository;
import com.example.EasyRoom.authentication.domain.service.UserDomainService;
import com.example.EasyRoom.authentication.domain.valueobject.Email;
import com.example.EasyRoom.authentication.domain.valueobject.Password;
import com.example.EasyRoom.shared.exception.DomainException;

public class UpdateProfileUseCaseImpl implements UpdateProfileUseCase {

    private final UserRepository userRepository;
    private final UserDomainService userDomainService;

    public UpdateProfileUseCaseImpl(UserRepository userRepository, UserDomainService userDomainService) {
        this.userRepository = userRepository;
        this.userDomainService = userDomainService;
    }

    @Override
    public UserResponseDto execute(Long userId, UserRequestDto request) {
        try {
            if (userId == null) {
                throw new IllegalArgumentException("User ID cannot be null");
            }

            userDomainService.validateEmail(request.getEmail());
            userDomainService.validatePassword(request.getPassword());

            UserAggregate userAggregate = userRepository.findById(userId);
            if (userAggregate == null) {
                throw new IllegalArgumentException("User not found");
            }

            Email email = new Email(request.getEmail());
            Password password = new Password(request.getPassword());
            userAggregate.setEmail(email);
            userAggregate.setPassword(password);

            UserAggregate updatedUser = userRepository.save(userAggregate);

            return new UserResponseDto(updatedUser.getId(), updatedUser.getEmail().getValue());
        } catch (IllegalArgumentException e) {
            throw new DomainException("Failed to update profile: " + e.getMessage(), e);
        }
    }
}