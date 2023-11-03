package com.example.UltiOauth.Config;

import com.example.UltiOauth.Entity.RegistrationSource;
import com.example.UltiOauth.Entity.UserEntity;
import com.example.UltiOauth.Entity.UserRole;
import com.example.UltiOauth.JWT.JwtProvider;
import com.example.UltiOauth.Service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
@Slf4j
public class OAuth2LoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    private final UserService userService;

    @Value("${app.frontEndUrl}")
    private String frontEndUrl;


    @Autowired
    private JwtProvider jwtProvider;

    public String authenticateStudent(String name, String link, UserRole role, String password){
        log.info("STARTING CREATING JWT TOKEN");
        // Authenticate the user using the authentication manager
        Authentication authenticated = SecurityContextHolder.getContext().getAuthentication();

        // Generate the JWT token
        String jwtToken = jwtProvider.generateToken(authenticated);
        log.info("CREATED JWT TOKEN SUCCESSFULLY");
        return jwtToken;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws ServletException, IOException {

        log.info("AUTHENTICATION BY GITHUB SUCCESSFULLY");
        OAuth2AuthenticationToken oAuth2AuthenticationToken = (OAuth2AuthenticationToken) authentication;

        if ("github".equals(oAuth2AuthenticationToken.getAuthorizedClientRegistrationId())) {

            DefaultOAuth2User principal = (DefaultOAuth2User) authentication.getPrincipal();
            Map<String, Object> attributes = principal.getAttributes();

            String link = attributes.getOrDefault("html_url", "").toString();
            String name = attributes.getOrDefault("login", "").toString();
            String password = attributes.getOrDefault("id", "1234567").toString();
            userService.findByLink(link)
                    .ifPresentOrElse(user -> {
                        log.info("USER EXISTED IN DATABASE");
                        DefaultOAuth2User newUser = new DefaultOAuth2User(List.of(new SimpleGrantedAuthority(user.getRole().name())),
                                attributes, "id");
                        Authentication securityAuth = new OAuth2AuthenticationToken(newUser, List.of(new SimpleGrantedAuthority(user.getRole().name())),
                                oAuth2AuthenticationToken.getAuthorizedClientRegistrationId());
                        SecurityContextHolder.getContext().setAuthentication(securityAuth);
                    }, () -> {
                        log.info("USER IS NOT EXISTED IN DATABASE");
                        UserEntity userEntity = new UserEntity();
                        if(name.equals("vtt232")){
                            log.info("USER WILL HAVE ADMIN ROLE");
                            userEntity.setRole(UserRole.ROLE_ADMIN);
                        }else{
                            log.info("USER WILL HAVE USER ROLE");
                            userEntity.setRole(UserRole.ROLE_USER);
                        }
                        userEntity.setLink(link);
                        userEntity.setUsername(name);
                        userEntity.setSource(RegistrationSource.GITHUB);
                        userEntity.setPassword(password);
                        userService.createUser(userEntity);
                        DefaultOAuth2User newUser = new DefaultOAuth2User(List.of(new SimpleGrantedAuthority(userEntity.getRole().name())),
                                attributes, "id");
                        Authentication securityAuth = new OAuth2AuthenticationToken(newUser, List.of(new SimpleGrantedAuthority(userEntity.getRole().name())),
                                oAuth2AuthenticationToken.getAuthorizedClientRegistrationId());
                        SecurityContextHolder.getContext().setAuthentication(securityAuth);
                    });


            String tolken = authenticateStudent(name, link, UserRole.ROLE_USER, password);

            if (tolken != null) {
                log.info("SETTING COOKIE");
                ResponseCookie springCookie = ResponseCookie.from("jwtToken", tolken)
                        .httpOnly(true)
                        .secure(false)
                        .path("/")
                        .maxAge(86400)
                        .build();
                // Add the JWT token cookie to the response
                response.addHeader(HttpHeaders.SET_COOKIE, springCookie.toString());
            }
            log.info("SETTING REDIRECTING URL");
            this.setAlwaysUseDefaultTargetUrl(true);
            this.setDefaultTargetUrl(frontEndUrl);
            super.onAuthenticationSuccess(request, response, authentication);
        }
    }
}
