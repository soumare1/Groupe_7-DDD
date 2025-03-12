package com.example.EasyRoom.authentication.domain.aggregate;

import com.example.EasyRoom.authentication.domain.valueobject.Email;
import com.example.EasyRoom.authentication.domain.valueobject.Password;

public class UserAggregate {
    private Long id;
    private Email email;
    private Password password;

    public UserAggregate(Email email, Password password) {
        this.email = email;
        this.password = password;
    }

    // Getters et setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Email getEmail() {
        return email;
    }

    public void setEmail(Email email) {
        this.email = email;
    }

    public Password getPassword() {
        return password;
    }

    public void setPassword(Password password) {
        this.password = password;
    }
}