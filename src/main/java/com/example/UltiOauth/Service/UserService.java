package com.example.UltiOauth.Service;

import com.example.UltiOauth.Entity.UserEntity;

import java.util.Optional;

public interface UserService {

    Optional<UserEntity> findByLink(String link);

    void save(UserEntity user);
}
