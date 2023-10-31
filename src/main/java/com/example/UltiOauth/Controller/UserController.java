package com.example.UltiOauth.Controller;

import com.example.UltiOauth.DTO.UserDTO;
import com.example.UltiOauth.Entity.UserEntity;
import com.example.UltiOauth.Entity.UserRole;
import com.example.UltiOauth.Service.UserService;
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
    @GetMapping("/infor")
    public ResponseEntity<UserDTO> getUserInformation(@AuthenticationPrincipal OAuth2User oAuth2User){
        if(oAuth2User == null){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
        String username = oAuth2User.getAttribute("login");
        UserDTO userDTO = userService.findByUsername(username);
        return ResponseEntity.ok().body(userDTO);
    }
}
