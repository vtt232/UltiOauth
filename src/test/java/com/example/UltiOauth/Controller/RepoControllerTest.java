package com.example.UltiOauth.Controller;

import com.example.UltiOauth.Config.OAuth2LoginSuccessHandler;
import com.example.UltiOauth.Config.SecurityConfig;
import com.example.UltiOauth.DTO.RepoDTO;
import com.example.UltiOauth.JWT.JwtProvider;
import com.example.UltiOauth.Service.RepoService;
import com.example.UltiOauth.Service.UserService;
import com.example.UltiOauth.Utils.JsonMapper;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.ConfigDataApplicationContextInitializer;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.samePropertyValuesAs;

import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.oauth2Login;

@WebMvcTest(RepoController.class)
@ContextConfiguration(classes = { RepoController.class,
        SecurityConfig.class, OAuth2LoginSuccessHandler.class},
        initializers = ConfigDataApplicationContextInitializer.class)
@AutoConfigureMockMvc
@Import({JsonMapper.class})
public class RepoControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    UserService userService;


    @MockBean
    RepoService repoService;

    @MockBean
    JwtProvider jwtProvider;

    @MockBean
    UserDetailsService userDetailsService;

    @MockBean
    WebClient webClient;

    @MockBean
    OAuth2User oAuth2User;

    @MockBean
    OAuth2AuthorizedClient client;

    private static final ObjectMapper mapper = new ObjectMapper();






    @Test
    void getAllRepos_shouldReturnAllRepos() throws Exception {

        RepoDTO repoDTO_1 = RepoDTO.builder().id(1).name("mobile").url("abc.com").language("java").build();
        RepoDTO repoDTO_2 = RepoDTO.builder().id(2).name("ai").url("xyz.com").language("python").build();
        RepoDTO repoDTO_3 = RepoDTO.builder().id(3).name("web").url("mnb.com").language("c++").build();

        // Mock the RepoService response
        List<RepoDTO> expectedRepoDTOs = new ArrayList<RepoDTO>();
        expectedRepoDTOs.add(repoDTO_1);
        expectedRepoDTOs.add(repoDTO_2);
        expectedRepoDTOs.add(repoDTO_3);


        when(repoService.getAllRepoByUsername(0, 4, "id", "vtt232")).thenReturn(expectedRepoDTOs);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/jwt/repo/").with(oauth2Login()
                .attributes(attrs -> attrs.put("login", "vtt232"))))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        // Assert the response content matches the expectedRepoDTOs
        String responseContent = result.getResponse().getContentAsString();
        List<RepoDTO> actualRepoDTOs = mapper.readValue(responseContent, new TypeReference<List<RepoDTO>>() {});

        // Assert that the actualRepoDTOs match the expectedRepoDTOs

        assertThat(actualRepoDTOs, samePropertyValuesAs(expectedRepoDTOs));
    }

    @Test
    void getAllRepos_shouldReturnUnauthorizedStatus() throws Exception {

        RepoDTO repoDTO_1 = RepoDTO.builder().id(1).name("mobile").url("abc.com").language("java").build();
        RepoDTO repoDTO_2 = RepoDTO.builder().id(2).name("ai").url("xyz.com").language("python").build();
        RepoDTO repoDTO_3 = RepoDTO.builder().id(3).name("web").url("mnb.com").language("c++").build();

        // Mock the RepoService response
        List<RepoDTO> expectedRepoDTOs = new ArrayList<RepoDTO>();
        expectedRepoDTOs.add(repoDTO_1);
        expectedRepoDTOs.add(repoDTO_2);
        expectedRepoDTOs.add(repoDTO_3);


        when(repoService.getAllRepoByUsername(0, 4, "id", "vtt232")).thenReturn(expectedRepoDTOs);

       mockMvc.perform(MockMvcRequestBuilders.get("/jwt/repo/"))
                .andExpect(MockMvcResultMatchers.status().isUnauthorized());
    }

}
