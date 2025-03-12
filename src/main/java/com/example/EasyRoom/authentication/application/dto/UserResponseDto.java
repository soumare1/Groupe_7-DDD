package com.example.EasyRoom.authentication.application.dto;

public class UserResponseDto {
    private Long id;
    private String email;

    public UserResponseDto(Long id, String email) {
        this.id = id;
        this.email = email;
    }

    // Getters et setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}