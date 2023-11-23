package com.example.UltiOauth.Controller;


import com.example.UltiOauth.Config.OAuth2LoginSuccessHandler;
import com.example.UltiOauth.Config.SecurityConfig;
import com.example.UltiOauth.DTO.AdminRequestDTO;
import com.example.UltiOauth.DTO.NoteDTO;
import com.example.UltiOauth.JWT.JwtProvider;
import com.example.UltiOauth.Service.*;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.ConfigDataApplicationContextInitializer;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.reactive.function.client.WebClient;


import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Arrays;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.oauth2Login;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AdminController.class)
@ContextConfiguration(classes = { AdminController.class,
        SecurityConfig.class, OAuth2LoginSuccessHandler.class},
        initializers = ConfigDataApplicationContextInitializer.class)
@AutoConfigureMockMvc
public class AdminControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    UserService userService;


    @MockBean
    RepoService repoService;

    @MockBean
    AdminService adminService;

    @MockBean
    JwtProvider jwtProvider;

    @MockBean
    UserDetailsService userDetailsService;

    @MockBean
    WebClient webClient;



    private static final ObjectMapper mapper = new ObjectMapper();

    @Test
    @WithMockUser(roles="ADMIN")
    public void setAdminRole_shouldReturnTrue() throws Exception {


        String username = "abcxyz";

        given(adminService.setAdminRole(username)).willReturn(true);

        mockMvc.perform(post("/api/jwt/admin/create-admin")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"usernameOfAdmin\": \"abcxyz\"}").with(oauth2Login()
                                .attributes(attrs -> attrs.put("login", "vtt232")).authorities(new SimpleGrantedAuthority("ROLE_ADMIN"))))
                .andExpect(status().isOk())
                .andExpect(content().string("true"));
    }

    @Test
    public void setAdminRole_shouldReturnFalse() throws Exception {
        String username = "abcxyz";

        given(adminService.setAdminRole(username)).willReturn(false);

        mockMvc.perform(post("/api/jwt/admin/create-admin")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"usernameOfAdmin\": \"abcxyz\"}").with(oauth2Login()
                                .attributes(attrs -> attrs.put("login", "vtt232")).authorities(new SimpleGrantedAuthority("ROLE_ADMIN"))))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("false"));
    }


    @Test
    public void setAdminRole_shouldReturnRedirectStatus() throws Exception {
        String username = "abcxyz";

        given(adminService.setAdminRole(username)).willReturn(false);

        mockMvc.perform(post("/api/jwt/admin/create-admin")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"usernameOfAdmin\": \"abcxyz\"}"))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    public void setAdminRole_shouldReturnForbiddenError() throws Exception {
        String username = "abcxyz";

        given(adminService.setAdminRole(username)).willReturn(false);

        mockMvc.perform(post("/api/jwt/admin/create-admin")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"usernameOfAdmin\": \"abcxyz\"}").with(oauth2Login()
                                .attributes(attrs -> attrs.put("login", "vtt232"))))
                .andExpect(status().isForbidden());
    }
}
