package com.example.UltiOauth.Controller;

import com.example.UltiOauth.Config.OAuth2LoginSuccessHandler;
import com.example.UltiOauth.Config.SecurityConfig;
import com.example.UltiOauth.DTO.UserDTO;
import com.example.UltiOauth.JWT.JwtProvider;
import com.example.UltiOauth.Service.UserService;
import com.example.UltiOauth.Utils.JsonMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.ConfigDataApplicationContextInitializer;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.oauth2Login;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
@ContextConfiguration(classes = { UserController.class,
        SecurityConfig.class, OAuth2LoginSuccessHandler.class},
        initializers = ConfigDataApplicationContextInitializer.class)
@AutoConfigureMockMvc
@Import({JsonMapper.class})
public class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    UserService userService;

    @MockBean
    JwtProvider jwtProvider;

    @MockBean
    UserDetailsService userDetailsService;

    private static final JsonMapper mapper = new JsonMapper();

    @Test
    void getUserInformation_shouldReturnUserInfor() throws Exception {

        UserDTO expectedUserDTO = UserDTO.builder()
                .login("vtt23")
                .url("https://github.com/vtt232")
                .build();

        when(userService.findByUsername(any())).thenReturn(expectedUserDTO);


        mockMvc.perform(MockMvcRequestBuilders.get("/api/jwt/user/infor").with(oauth2Login()))
                .andExpect(status().isOk())
                .andExpect(content().string(mapper.stringify(expectedUserDTO)));


    }

    @Test
    void getUserInformation_shouldReturnUnauthorizedStatus() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/api/jwt/user/infor"))
                .andExpect(status().isUnauthorized())
                .andReturn();

        assertEquals(401, result.getResponse().getStatus(), "Expected an Unauthorized status");
    }
}
