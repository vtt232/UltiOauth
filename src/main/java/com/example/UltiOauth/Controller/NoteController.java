package com.example.UltiOauth.Controller;

import com.example.UltiOauth.Annotation.RoutingWith;
import com.example.UltiOauth.DTO.NoteDTO;
import com.example.UltiOauth.DTO.RepoDTO;
import com.example.UltiOauth.Service.NoteService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/jwt/note")
@Slf4j
public class NoteController {

    private final NoteService noteService;


    public NoteController(NoteService noteService) {
        this.noteService = noteService;
    }

    @GetMapping(path = "",produces = "application/json")
    @RoutingWith("${app.slavedb}")
    public ResponseEntity<List<NoteDTO>> getAllNotesByRepoId(@RequestParam(name="repoId", defaultValue = "0") long repoId, @AuthenticationPrincipal OAuth2User oAuth2User)
    {
        if(oAuth2User == null){
            log.warn("USER IS NOT AUTHENTICATED");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
        String username = oAuth2User.getAttribute("login");
        List<NoteDTO> result = noteService.getAllNotesByRepoIdAndUsername(username, repoId);
        log.info("GET ALL NOTES SUCCESSFULLY");
        return ResponseEntity.ok(result);
    }

    @PostMapping(path="/{repoId}",produces = "application/json", consumes = "application/json")
    @RoutingWith("${app.masterdb}")
    public ResponseEntity<List<NoteDTO>> addNote(@RequestBody NoteDTO newNote, @AuthenticationPrincipal OAuth2User oAuth2User,
                                 @PathVariable long repoId) {
        if(oAuth2User == null){
            log.warn("USER IS NOT AUTHENTICATED");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }

        String username = oAuth2User.getAttribute("login");

        List<NoteDTO> noteList = noteService.addNote(newNote, username, repoId);
        log.info("ADD NOTES SUCCESSFULLY");
        return ResponseEntity.status(HttpStatus.CREATED).body(noteList);
    }

    @PutMapping(path="/{repoId}/{noteId}",produces = "application/json", consumes = "application/json")
    @RoutingWith("${app.masterdb}")
    public ResponseEntity<List<NoteDTO>> updateNote(@RequestBody NoteDTO updateNote, @AuthenticationPrincipal OAuth2User oAuth2User,
                                 @PathVariable long repoId, @PathVariable long noteId) {
        if(oAuth2User == null){
            log.warn("USER IS NOT AUTHENTICATED");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
        String username = oAuth2User.getAttribute("login");
        List<NoteDTO> noteList = noteService.updateNote(updateNote, username, repoId, noteId);
        log.info("UPDATE NOTES SUCCESSFULLY");
        return ResponseEntity.ok(noteList);
    }

    @DeleteMapping(path="/{repoId}/{noteId}",produces = "application/json")
    @RoutingWith("${app.masterdb}")
    public ResponseEntity<List<NoteDTO>> deleteNote(@AuthenticationPrincipal OAuth2User oAuth2User,
                                    @PathVariable long repoId, @PathVariable long noteId) {
        if(oAuth2User == null){
            log.warn("USER IS NOT AUTHENTICATED");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
        String username = oAuth2User.getAttribute("login");
        List<NoteDTO> noteList = noteService.deleteNote(username, repoId, noteId);
        log.info("DELETE NOTES SUCCESSFULLY");
        return ResponseEntity.ok(noteList);
    }

}
