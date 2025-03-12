package com.example.EasyRoom.authentication.domain.repository;

import com.example.EasyRoom.authentication.domain.aggregate.UserAggregate;
import com.example.EasyRoom.authentication.domain.valueobject.Email;

public interface UserRepository {
    UserAggregate save(UserAggregate userAggregate);
    UserAggregate findByEmail(Email email);
    UserAggregate findById(Long id);
    void delete(UserAggregate userAggregate);
}