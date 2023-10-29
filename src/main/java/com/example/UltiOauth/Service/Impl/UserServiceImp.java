package com.example.UltiOauth.Service.Impl;

import com.example.UltiOauth.Entity.UserEntity;
import com.example.UltiOauth.Repository.UserRepository;
import com.example.UltiOauth.Service.UserService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImp implements UserService {

    private final UserRepository userRepository;

    public UserServiceImp(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Optional<UserEntity> findByLink(String link) {
        return userRepository.findByLink(link);
    }

    @Override
    public void save(UserEntity user) {
        userRepository.save(user);
    }
}
