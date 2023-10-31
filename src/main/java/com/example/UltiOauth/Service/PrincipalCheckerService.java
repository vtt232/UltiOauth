package com.example.UltiOauth.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.stereotype.Service;

@Service
public class PrincipalCheckerService {


    @Scheduled(fixedRate = 5000) // Run this method every 5 seconds
    public void checkPrincipal() {
        Authentication auth = (Authentication) SecurityContextHolder.getContext().getAuthentication();
        DefaultOAuth2User principal = (DefaultOAuth2User) auth.getPrincipal();

        if (principal != null) {
            // The user is authenticated
            String username = principal.getAttribute("login");
            // You can perform actions with the authenticated user (principal) here
            System.out.println("Authenticated user: " + username);
        } else {
            System.out.println("No authenticated user.");
        }
    }
}