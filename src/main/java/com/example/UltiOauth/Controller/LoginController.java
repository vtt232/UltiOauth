package com.example.UltiOauth.Controller;

import com.example.UltiOauth.DTO.RepoDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class LoginController {

    @GetMapping("/access/status")
    @ResponseBody
    public ResponseEntity<Boolean> checkLogin(@AuthenticationPrincipal OAuth2User oAuth2User) {
        if(oAuth2User == null){
            return ResponseEntity.ok(false);
        }
        return ResponseEntity.ok(true);
    }

    @GetMapping("/access/redirect")
    public ResponseEntity<String> getRedirectLink() {
        return ResponseEntity.ok("http://localhost:8080/access/login");
    }

    @GetMapping("/access/login")
    public String getLoginPage() {
        return "login";
    }
}