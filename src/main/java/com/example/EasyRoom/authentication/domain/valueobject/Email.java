package com.example.EasyRoom.authentication.domain.valueobject;

import java.util.Objects;

public class Email {
    private final String value;

    public Email(String value) {
        if (value == null || value.isEmpty()) {
            throw new IllegalArgumentException("Email cannot be null or empty");
        }
        if (!value.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            throw new IllegalArgumentException("Invalid email format");
        }
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Email email = (Email) o;
        return Objects.equals(value, email.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}