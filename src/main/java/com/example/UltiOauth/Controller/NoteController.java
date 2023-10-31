package com.example.UltiOauth.Controller;

import com.example.UltiOauth.DTO.NoteDTO;
import com.example.UltiOauth.DTO.RepoDTO;
import com.example.UltiOauth.Service.NoteService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("jwt/note")
public class NoteController {

    private final NoteService noteService;


    public NoteController(NoteService noteService) {
        this.noteService = noteService;
    }

    @GetMapping(path = "",produces = "application/json")
    public List<NoteDTO> getAllNotesByRepoId(@RequestParam(name="repoId", defaultValue = "0") long repoId,
                                     @AuthenticationPrincipal OAuth2User oAuth2User)
    {
        String username = oAuth2User.getAttribute("login");
        return noteService.getAllNotesByRepoIdAndUsername(username, repoId);
    }

    @PostMapping(path="/{repoId}",produces = "application/json", consumes = "application/json")
    public List<NoteDTO> addNote(@RequestBody NoteDTO newNote, @AuthenticationPrincipal OAuth2User oAuth2User,
                                 @PathVariable long repoId) {
        String username = oAuth2User.getAttribute("login");
        List<NoteDTO> noteList = noteService.addNote(newNote, username, repoId);
        return noteList;
    }

    @PutMapping(path="/{repoId}/{noteId}",produces = "application/json", consumes = "application/json")
    public List<NoteDTO> updateNote(@RequestBody NoteDTO updateNote, @AuthenticationPrincipal OAuth2User oAuth2User,
                                 @PathVariable long repoId, @PathVariable long noteId) {
        String username = oAuth2User.getAttribute("login");
        List<NoteDTO> noteList = noteService.updateNote(updateNote, username, repoId, noteId);
        return noteList;
    }

    @DeleteMapping(path="/{repoId}/{noteId}",produces = "application/json")
    public List<NoteDTO> deleteNote(@AuthenticationPrincipal OAuth2User oAuth2User,
                                    @PathVariable long repoId, @PathVariable long noteId) {
        String username = oAuth2User.getAttribute("login");
        List<NoteDTO> noteList = noteService.deleteNote(username, repoId, noteId);
        return noteList;
    }

}
