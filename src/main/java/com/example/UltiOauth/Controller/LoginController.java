package com.example.UltiOauth.Controller;

import com.example.UltiOauth.DTO.RepoDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@Slf4j
public class LoginController {

    @Value("${app.loginPageUrl}")
    private String loginPageUrl;

    @GetMapping("/access/status")
    @ResponseBody
    public ResponseEntity<Boolean> checkLogin(@AuthenticationPrincipal OAuth2User oAuth2User) {
        log.info("USER CHECK AUTHENTICATION STATUS");
        if(oAuth2User == null){
            log.warn("USER IS NOT AUTHENTICATED");
            return ResponseEntity.ok(false);
        }
        log.info("USER IS AUTHENTICATED");
        return ResponseEntity.ok(true);
    }

    @GetMapping("/access/redirect")
    public ResponseEntity<String> getRedirectLink() {
        log.info("USER REQUIRE LOGIN PAGE LINK");
        return ResponseEntity.ok(loginPageUrl);
    }

    @GetMapping("/access/login")
    public String getLoginPage() {
        log.info("USER REQUIRE LOGIN PAGE VIEW");
        return "login";
    }
}