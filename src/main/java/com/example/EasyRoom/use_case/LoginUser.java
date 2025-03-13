package com.example.EasyRoom.use_case;

import com.example.EasyRoom.authentication.domain.aggregate.UserAggregate;
import com.example.EasyRoom.authentication.domain.repository.UserRepository;
import com.example.EasyRoom.authentication.domain.service.UserDomainService;
import com.example.EasyRoom.authentication.domain.valueobject.Email;
import com.example.EasyRoom.authentication.application.dto.UserRequestDto;
import com.example.EasyRoom.authentication.application.dto.UserResponseDto;
import com.example.EasyRoom.shared.exception.DomainException;

public class LoginUser {

    private final UserRepository userRepository;
    private final UserDomainService userDomainService;

    public LoginUser(UserRepository userRepository, UserDomainService userDomainService) {
        this.userRepository = userRepository;
        this.userDomainService = userDomainService;
    }

    public UserResponseDto execute(UserRequestDto request) {
        // 1. Setup (Arrange): Préparer les données et effectuer les validations initiales
        if (request == null || request.getEmail() == null || request.getPassword() == null) {
            throw new IllegalArgumentException("Request, email, or password cannot be null");
        }

        Email email = new Email(request.getEmail());
        userDomainService.validateEmail(request.getEmail());

        // 2. Exercise (Act): Exécuter l'action principale
        UserAggregate user = userRepository.findByEmail(email);
        if (user == null) {
            throw new DomainException("Invalid email or password");
        }

        userDomainService.validatePassword(request.getPassword());

        // 3. Verify (Assert): Vérifier les résultats et gérer les effets secondaires
        // Ici, on pourrait ajouter une journalisation ou un événement pour indiquer une connexion réussie
        return new UserResponseDto(user.getId(), user.getEmail().getValue());
    }
}