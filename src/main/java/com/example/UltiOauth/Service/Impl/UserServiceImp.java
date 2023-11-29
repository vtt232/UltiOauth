package com.example.UltiOauth.Service.Impl;

import com.example.UltiOauth.DTO.UserDTO;
import com.example.UltiOauth.Entity.UserEntity;
import com.example.UltiOauth.Exception.UserExistedException;
import com.example.UltiOauth.Exception.UserNotFoundException;
import com.example.UltiOauth.Mapper.UserMapper;
import com.example.UltiOauth.Repository.UserRepository;
import com.example.UltiOauth.Service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
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
    public UserDTO findByUsername(String username) {
        log.info("STARTING FINDING USER BY USERNAME");
        Optional<UserEntity> userEntity = userRepository.findByUsername(username);
        if(userEntity.isPresent()){
            log.info("FOUND USER");
            UserDTO userDTO = UserMapper.fromEntityDto(userEntity.get());
            return userDTO;
        }else{
            throw new UserNotFoundException("User " + username + " not found");
        }
    }

    @Override
    public UserDTO createUser(UserEntity user) {
        log.info("STARTING FINDING USER BY USERNAME TO CREATING USER");
        Optional<UserEntity> existedUserEntity = userRepository.findByUsername(user.getUsername());

        if(existedUserEntity.isPresent()){
            log.warn("USER IS EXISTED");
            throw new UserExistedException(user.getUsername());
        }
        else{
            log.info("CREATING NEW USER");
            return UserMapper.fromEntityDto(userRepository.save(user));
        }
    }
}
