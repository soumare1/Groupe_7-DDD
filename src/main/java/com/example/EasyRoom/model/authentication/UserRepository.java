package com.example.EasyRoom.model.authentication;

public interface UserRepository {
    User findByEmail(String email);
}