package com.example.UltiOauth.Service.Impl;

import com.example.UltiOauth.Entity.UserEntity;
import com.example.UltiOauth.Entity.UserRole;
import com.example.UltiOauth.Exception.UserNotFoundException;
import com.example.UltiOauth.Repository.UserRepository;
import com.example.UltiOauth.Service.AdminService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
public class AdminServiceImpl implements AdminService {

    private UserRepository userRepository;

    public AdminServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public boolean setAdminRole(String username){
        log.info("STARTING FINDING USER");
        Optional<UserEntity> userEntity = userRepository.findByUsername(username);
        if(userEntity.isPresent()){
            log.info("USER IS FOUND");
            userEntity.get().setRole(UserRole.ROLE_ADMIN);
            userRepository.save(userEntity.get());
            log.info("USER IS ADDED ADMIN ROLE");
            return true;
        }
        else{
            log.warn("USER IS NOT FOUND");
            throw new UserNotFoundException(username);
        }
    }
}
