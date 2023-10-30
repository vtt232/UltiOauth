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

    @GetMapping(path = "/{repoName}",produces = "application/json")
    public List<NoteDTO> getAllNotes(@RequestParam(name="page", defaultValue = "0") int pageNo,
                                     @RequestParam(name="size", defaultValue = "2") int pageSize,
                                     @RequestParam(name="sort",defaultValue = "id") String sortBy,
                                     @AuthenticationPrincipal OAuth2User oAuth2User,
                                     @PathVariable String repoName)
    {
        String username = oAuth2User.getAttribute("login");
        return noteService.getAllNotesByRepoNameAndUsername(pageNo, pageSize, sortBy, username, repoName);
    }

    @PostMapping(path="/{repoName}",produces = "application/json", consumes = "application/json")
    public List<NoteDTO> addNote(@RequestBody NoteDTO newNote, @AuthenticationPrincipal OAuth2User oAuth2User,
                                 @PathVariable String repoName) {
        String username = oAuth2User.getAttribute("login");
        List<NoteDTO> noteList = noteService.addNote(newNote, username, repoName);
        if (noteList == null) {
            return null;
        }
        else {
            return noteList;
        }
    }


}
