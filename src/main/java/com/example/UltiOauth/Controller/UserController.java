package com.example.UltiOauth.Controller;

import com.example.UltiOauth.Entity.UserEntity;
import com.example.UltiOauth.Entity.UserRole;
import com.example.UltiOauth.Service.UserService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/user/{email}")
    public void changeToAdmin(@PathVariable String link) {
        Optional<UserEntity> userEntity = userService.findByLink(link);
        if(userEntity.isPresent()){
            userEntity.get().setRole(UserRole.ROLE_ADMIN);
            userService.save(userEntity.get());
        }
    }
}
