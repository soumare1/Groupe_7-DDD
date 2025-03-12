package com.example.EasyRoom.authentication.application.usecase.impl;

import com.example.EasyRoom.authentication.application.dto.UserRequestDto;
import com.example.EasyRoom.authentication.application.dto.UserResponseDto;
import com.example.EasyRoom.authentication.application.usecase.LoginUserUseCase;
import com.example.EasyRoom.authentication.domain.aggregate.UserAggregate;
import com.example.EasyRoom.authentication.domain.repository.UserRepository;
import com.example.EasyRoom.authentication.domain.service.UserDomainService;
import com.example.EasyRoom.authentication.domain.valueobject.Email;
import com.example.EasyRoom.shared.exception.DomainException;

public class LoginUserUseCaseImpl implements LoginUserUseCase {

    private final UserRepository userRepository;
    private final UserDomainService userDomainService;

    public LoginUserUseCaseImpl(UserRepository userRepository, UserDomainService userDomainService) {
        this.userRepository = userRepository;
        this.userDomainService = userDomainService;
    }

    @Override
    public UserResponseDto execute(UserRequestDto request) {
        try {
            userDomainService.validateEmail(request.getEmail());
            userDomainService.validatePassword(request.getPassword());

            Email email = new Email(request.getEmail());
            UserAggregate userAggregate = userRepository.findByEmail(email);
            if (userAggregate == null || !userAggregate.getPassword().getValue().equals(request.getPassword())) {
                throw new IllegalArgumentException("Invalid email or password");
            }

            return new UserResponseDto(userAggregate.getId(), userAggregate.getEmail().getValue());
        } catch (IllegalArgumentException e) {
            throw new DomainException("Login failed: " + e.getMessage(), e);
        }
    }
}