package com.example.UltiOauth.Controller;

import com.example.UltiOauth.Config.OAuth2LoginSuccessHandler;
import com.example.UltiOauth.Config.SecurityConfig;
import com.example.UltiOauth.JWT.JwtProvider;
import com.example.UltiOauth.Service.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.ConfigDataApplicationContextInitializer;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.authentication;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.oauth2Login;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(LoginController.class)
@ContextConfiguration(classes = { LoginController.class,
        SecurityConfig.class, OAuth2LoginSuccessHandler.class},
        initializers = ConfigDataApplicationContextInitializer.class)
@AutoConfigureMockMvc
public class LoginControllerTest {

    @Value("${app.loginPageUrl}")
    private String loginPageUrl;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    UserService userService;

    @MockBean
    JwtProvider jwtProvider;

    @MockBean
    UserDetailsService userDetailsService;


    @Test
    @DisplayName("CHECK AUTHENTICATION STATUS OF CLIENT. CLIENT IS AUTHENTICATED IN THIS TEST")
    void checkAuthenticationStatus_shouldReturnTrue() throws Exception{

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/api/access/status").with(oauth2Login()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        String responseContent = result.getResponse().getContentAsString();
        boolean isAuthenticated = Boolean.parseBoolean(responseContent);


        assertEquals(true, isAuthenticated, "User should be authenticated");

    }

    @Test
    @DisplayName("CHECK AUTHENTICATION STATUS OF CLIENT. CLIENT IS NOT AUTHENTICATED IN THIS TEST")
    void checkAuthenticationStatus_shouldReturnFalse() throws Exception{

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/api/access/status"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        String responseContent = result.getResponse().getContentAsString();
        boolean isAuthenticated = Boolean.parseBoolean(responseContent);


        assertEquals(false, isAuthenticated, "User should not be authenticated");

    }

    @Test
    @DisplayName("USER REQUIRE REDIRECT LINK TO LOGIN BY GITHUB PAGE. SHOULD RETURN THE CORRECT LINK TO USER")
    void giveRedirectLoginLink_shouldReturnTheLink() throws Exception{
        MvcResult result =mockMvc.perform(get("/api/access/redirect")).andReturn();
        String content = result.getResponse().getContentAsString();
        int status = result.getResponse().getStatus();


        assertEquals(200, status, "Expected a status code of 200");


        assertEquals(loginPageUrl, content, "Expected redirect link");

    }

    @Test
    @DisplayName("USER REDIRECT TO LOGIN BY GITHUB PAGE. SHOULD RETURN THE CORRECT VIEW TO USER")
    void testGetLoginPage() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/api/access/login"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        String viewName = result.getModelAndView().getViewName();

        // Assert that the returned view name is "login"
        assertEquals("login", viewName, "View name should be 'login'");
    }


}
