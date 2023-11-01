package com.example.UltiOauth.Controller;

import com.example.UltiOauth.DTO.UserDTO;
import com.example.UltiOauth.Entity.UserEntity;
import com.example.UltiOauth.Entity.UserRole;
import com.example.UltiOauth.Service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("jwt/user/")
@Slf4j
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

//    @PostMapping("create-admin/{username}")
//    public void changeToAdmin(@PathVariable String username) {
//        Optional<UserEntity> userEntity = userService.findByUsername(username);
//        if(userEntity.isPresent()){
//            userEntity.get().setRole(UserRole.ROLE_ADMIN);
//            userService.save(userEntity.get());
//        }
//    }
    @GetMapping(path="/infor", produces = "application/json")
    public ResponseEntity<UserDTO> getUserInformation(@AuthenticationPrincipal OAuth2User oAuth2User){
        if(oAuth2User == null){
            log.warn("USER IS NOT AUTHENTICATED");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
        String username = oAuth2User.getAttribute("login");
        log.info("USER %s REQUIRE THEIR INFORMATION", username);
        UserDTO userDTO = userService.findByUsername(username);
        log.info("USER %s GET THEIR INFORMATION SUCCESS", username);
        return ResponseEntity.ok().body(userDTO);
    }
}
