package com.example.UltiOauth.Service.Impl;

import com.example.UltiOauth.DTO.UserDTO;
import com.example.UltiOauth.DTO.WebSocketAnnouncementDTO;
import com.example.UltiOauth.Entity.ServerEvent;
import com.example.UltiOauth.Entity.UserEntity;
import com.example.UltiOauth.Event.UpdateSystemStatisticsEvent;
import com.example.UltiOauth.Exception.UserExistedException;
import com.example.UltiOauth.Exception.UserNotFoundException;
import com.example.UltiOauth.Mapper.UserMapper;
import com.example.UltiOauth.Repository.UserRepository;
import com.example.UltiOauth.Service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.kafka.KafkaException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
public class UserServiceImp implements UserService {

    private final UserRepository userRepository;

    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    public UserServiceImp(UserRepository userRepository, ApplicationEventPublisher applicationEventPublisher) {
        this.userRepository = userRepository;
        this.applicationEventPublisher = applicationEventPublisher;
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

            try {
                UserDTO newUserDTO = UserMapper.fromEntityDto(userRepository.save(user));
                log.info("CREATED NEW USER SUCCESSFULLY");

                WebSocketAnnouncementDTO webSocketAnnouncementDTO = new WebSocketAnnouncementDTO(ServerEvent.CHANGE_NUMBER_OF_USER, newUserDTO.getLogin());
                UpdateSystemStatisticsEvent updateSystemStatisticsEvent = new UpdateSystemStatisticsEvent(this, webSocketAnnouncementDTO);
                applicationEventPublisher.publishEvent(updateSystemStatisticsEvent);

                return newUserDTO;

            } catch (KafkaException exception) {
                log.error("CREATING NEW USER FAILED");
                throw exception;
            }



        }
    }
}
