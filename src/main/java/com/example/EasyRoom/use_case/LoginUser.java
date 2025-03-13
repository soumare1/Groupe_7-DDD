package com.example.EasyRoom.use_case;

import com.example.EasyRoom.model.authentication.User;
import com.example.EasyRoom.model.authentication.UserRepository;
import com.example.EasyRoom.model.authentication.UserValidator;

public class LoginUser {

    private final UserRepository userRepository;
    private final UserValidator userValidator;

    public LoginUser(UserRepository userRepository, UserValidator userValidator) {
        this.userRepository = userRepository;
        this.userValidator = userValidator;
    }

    public User execute(String email, String password) {
        // 1. Setup (Arrange): Préparer les données et effectuer les validations initiales
        userValidator.validateEmail(email);
        userValidator.validatePassword(password);

        // 2. Exercise (Act): Exécuter l'action principale
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new UserNotFoundException();
        }

        if (!user.getPassword().equals(password)) {
            throw new IllegalArgumentException("Invalid password");
        }

        // 3. Verify (Assert): Vérifier les résultats et gérer les effets secondaires
        // Ici, on pourrait ajouter une journalisation ou un événement pour indiquer une connexion réussie
        return user;
    }
}