package com.example.UltiOauth.Service;

import com.example.UltiOauth.DTO.UserDTO;
import com.example.UltiOauth.Entity.UserEntity;

import java.util.Optional;

public interface UserService {

    Optional<UserEntity> findByLink(String link);
    UserDTO findByUsername(String username);

    void createUser(UserEntity user);
}
