package com.example.UltiOauth.Service.Impl;


import com.example.UltiOauth.Entity.UserPrincipal;
import com.example.UltiOauth.Exception.UserNotAuthenticated;
import com.example.UltiOauth.Exception.UserNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;

import java.util.Optional;
@Slf4j
public class AuditorAwareImpl implements AuditorAware<String> {


    @Override
    public Optional<String> getCurrentAuditor() {
        log.info("STARTING GETTING CURRENT AUDITOR");
        if (SecurityContextHolder.getContext().getAuthentication() != null) {
            Authentication auth = (Authentication) SecurityContextHolder.getContext().getAuthentication();
            DefaultOAuth2User principal = (DefaultOAuth2User) auth.getPrincipal();
            Optional<String> res = Optional.of(principal.getAttribute("login"));
            log.info("GET CURRENT AUDITOR SUCCESSFULLY");
            return res;
        } else {
            throw new UserNotAuthenticated();
        }
    }

}
