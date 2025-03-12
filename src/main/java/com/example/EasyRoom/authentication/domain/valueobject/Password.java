package com.example.EasyRoom.authentication.domain.valueobject;

import java.util.Objects;

public class Password {
    private final String value;

    public Password(String value) {
        if (value == null || value.length() < 8) {
            throw new IllegalArgumentException("Password must be at least 8 characters long");
        }
        this.value = value; // À améliorer avec un hash si ct un vrai projet
    }

    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Password password = (Password) o;
        return Objects.equals(value, password.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}