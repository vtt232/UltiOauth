package com.example.UltiOauth.Controller;

import com.example.UltiOauth.Config.GlobalExceptionHandler;
import com.example.UltiOauth.Config.OAuth2LoginSuccessHandler;
import com.example.UltiOauth.Config.SecurityConfig;
import com.example.UltiOauth.DTO.NoteDTO;
import com.example.UltiOauth.DTO.RepoDTO;
import com.example.UltiOauth.Exception.NoteNotFoundException;
import com.example.UltiOauth.Exception.RepoNotFoundException;
import com.example.UltiOauth.JWT.JwtProvider;
import com.example.UltiOauth.Service.NoteService;
import com.example.UltiOauth.Service.RepoService;
import com.example.UltiOauth.Service.UserService;
import com.example.UltiOauth.Utils.JsonMapper;
import com.fasterxml.jackson.core.type.TypeReference;
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
import org.springframework.web.reactive.function.client.WebClient;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.samePropertyValuesAs;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.oauth2Login;

@WebMvcTest(NoteController.class)
@ContextConfiguration(classes = { NoteController.class,
        SecurityConfig.class, OAuth2LoginSuccessHandler.class, GlobalExceptionHandler.class},
        initializers = ConfigDataApplicationContextInitializer.class)
@AutoConfigureMockMvc
@Import({JsonMapper.class})
public class NoteControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    UserService userService;


    @MockBean
    RepoService repoService;

    @MockBean
    NoteService noteService;

    @MockBean
    JwtProvider jwtProvider;

    @MockBean
    UserDetailsService userDetailsService;

    @MockBean
    WebClient webClient;


    private static final ObjectMapper mapper = new ObjectMapper();

    @Test
    void getAllNotes_shouldReturnAllNotes() throws Exception {

        NoteDTO noteDTO_1 = NoteDTO.builder().id(1).content("hello").build();
        NoteDTO noteDTO_2 = NoteDTO.builder().id(2).content("hallo").build();
        NoteDTO noteDTO_3 = NoteDTO.builder().id(3).content("xin chao").build();

        List<NoteDTO> expectedNoteDTOs = new ArrayList<NoteDTO>();
        expectedNoteDTOs.add(noteDTO_1);
        expectedNoteDTOs.add(noteDTO_2);
        expectedNoteDTOs.add(noteDTO_3);


        when(noteService.getAllNotesByRepoIdAndUsername("vtt232", 1)).thenReturn(expectedNoteDTOs);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/api/jwt/note").param("repoId","1").with(oauth2Login()
                        .attributes(attrs -> attrs.put("login", "vtt232"))))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        String responseContent = result.getResponse().getContentAsString();
        List<NoteDTO> actualNoteDTOs = mapper.readValue(responseContent, new TypeReference<List<NoteDTO>>() {});


        assertThat(actualNoteDTOs, samePropertyValuesAs(expectedNoteDTOs));
    }

    @Test
    void getAllNotes_shouldReturnUnauthorizedStatus() throws Exception {

        NoteDTO noteDTO_1 = NoteDTO.builder().id(1).content("hello").build();
        NoteDTO noteDTO_2 = NoteDTO.builder().id(2).content("hallo").build();
        NoteDTO noteDTO_3 = NoteDTO.builder().id(3).content("xin chao").build();

        List<NoteDTO> expectedNoteDTOs = new ArrayList<NoteDTO>();
        expectedNoteDTOs.add(noteDTO_1);
        expectedNoteDTOs.add(noteDTO_2);
        expectedNoteDTOs.add(noteDTO_3);


        when(noteService.getAllNotesByRepoIdAndUsername("vtt232", 1)).thenReturn(expectedNoteDTOs);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/jwt/note").param("repoId","1"))
                .andExpect(MockMvcResultMatchers.status().isUnauthorized());
    }


    @Test
    void addNewNote_shouldReturnAllNotes() throws Exception {
        NoteDTO noteDTO_1 = NoteDTO.builder().id(1).content("hello").build();
        NoteDTO noteDTO_2 = NoteDTO.builder().id(2).content("hallo").build();
        NoteDTO noteDTO_3 = NoteDTO.builder().id(3).content("new content").build();


        List<NoteDTO> expectedNoteDTOs = new ArrayList<NoteDTO>();
        expectedNoteDTOs.add(noteDTO_1);
        expectedNoteDTOs.add(noteDTO_2);
        expectedNoteDTOs.add(noteDTO_3);

        when(noteService.addNote(any(NoteDTO.class),eq("vtt232"),eq(1L))).thenReturn(expectedNoteDTOs);


        NoteDTO newNote = NoteDTO.builder().content("new content").build();

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/api/jwt/note/1").contentType("application/json")
                        .content(mapper.writeValueAsString(newNote)).with(oauth2Login()
                        .attributes(attrs -> attrs.put("login", "vtt232"))))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andReturn();


        String responseContent = result.getResponse().getContentAsString();
        List<NoteDTO> actualNoteDTOs = new ObjectMapper().readValue(responseContent, new TypeReference<List<NoteDTO>>() {});

        assertThat(actualNoteDTOs, samePropertyValuesAs(expectedNoteDTOs));
    }

    @Test
    void addNewNote_shouldReturnRepoNotFoundException() throws Exception {
        NoteDTO noteDTO_1 = NoteDTO.builder().id(1).content("hello").build();
        NoteDTO noteDTO_2 = NoteDTO.builder().id(2).content("hallo").build();
        NoteDTO noteDTO_3 = NoteDTO.builder().id(3).content("new content").build();


        List<NoteDTO> expectedNoteDTOs = new ArrayList<NoteDTO>();
        expectedNoteDTOs.add(noteDTO_1);
        expectedNoteDTOs.add(noteDTO_2);
        expectedNoteDTOs.add(noteDTO_3);

        given(noteService.addNote(any(NoteDTO.class),eq("vtt232"),any(Long.class))).willThrow(new RepoNotFoundException("Repo is not found"));


        NoteDTO newNote = NoteDTO.builder().content("new content").build();

        mockMvc.perform(MockMvcRequestBuilders.post("/api/jwt/note/1").contentType("application/json")
                        .content(mapper.writeValueAsString(newNote)).with(oauth2Login()
                                .attributes(attrs -> attrs.put("login", "vtt232"))))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    void updateNote_shouldReturnAllNotes() throws Exception {
        NoteDTO noteDTO_1 = NoteDTO.builder().id(1).content("hello").build();
        NoteDTO noteDTO_2 = NoteDTO.builder().id(2).content("hallo").build();
        NoteDTO noteDTO_3 = NoteDTO.builder().id(3).content("update content").build();


        List<NoteDTO> expectedNoteDTOs = new ArrayList<NoteDTO>();
        expectedNoteDTOs.add(noteDTO_1);
        expectedNoteDTOs.add(noteDTO_2);
        expectedNoteDTOs.add(noteDTO_3);

        when(noteService.updateNote(any(NoteDTO.class),eq("vtt232"),eq(1L),eq(3L))).thenReturn(expectedNoteDTOs);

        NoteDTO newNote = NoteDTO.builder().content("update content").build();

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.put("/api/jwt/note/1/3").contentType("application/json")
                        .content(mapper.writeValueAsString(newNote)).with(oauth2Login()
                                .attributes(attrs -> attrs.put("login", "vtt232"))))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();


        String responseContent = result.getResponse().getContentAsString();
        List<NoteDTO> actualNoteDTOs = new ObjectMapper().readValue(responseContent, new TypeReference<List<NoteDTO>>() {});

        assertThat(actualNoteDTOs, samePropertyValuesAs(expectedNoteDTOs));
    }

    @Test
    void deletNote_shouldReturnAllNotes() throws Exception {
        NoteDTO noteDTO_1 = NoteDTO.builder().id(1).content("hello").build();
        NoteDTO noteDTO_2 = NoteDTO.builder().id(2).content("hallo").build();



        List<NoteDTO> expectedNoteDTOs = new ArrayList<NoteDTO>();
        expectedNoteDTOs.add(noteDTO_1);
        expectedNoteDTOs.add(noteDTO_2);


        when(noteService.deleteNote("vtt232",1L,3L)).thenReturn(expectedNoteDTOs);


        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.delete("/api/jwt/note/1/3").with(oauth2Login()
                                .attributes(attrs -> attrs.put("login", "vtt232"))))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();


        String responseContent = result.getResponse().getContentAsString();
        List<NoteDTO> actualNoteDTOs = new ObjectMapper().readValue(responseContent, new TypeReference<List<NoteDTO>>() {});

        assertThat(actualNoteDTOs, samePropertyValuesAs(expectedNoteDTOs));
    }

    @Test
    void deletNote_shouldReturnNoteNotFoundException() throws Exception {
        NoteDTO noteDTO_1 = NoteDTO.builder().id(1).content("hello").build();
        NoteDTO noteDTO_2 = NoteDTO.builder().id(2).content("hallo").build();



        List<NoteDTO> expectedNoteDTOs = new ArrayList<NoteDTO>();
        expectedNoteDTOs.add(noteDTO_1);
        expectedNoteDTOs.add(noteDTO_2);


        when(noteService.deleteNote(eq("vtt232"),eq(1L),any(Long.class))).thenThrow(new NoteNotFoundException("Note is not found"));


        mockMvc.perform(MockMvcRequestBuilders.delete("/api/jwt/note/1/9").with(oauth2Login()
                        .attributes(attrs -> attrs.put("login", "vtt232"))))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }





}
