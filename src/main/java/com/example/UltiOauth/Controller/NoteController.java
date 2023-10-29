package com.example.UltiOauth.Controller;

import com.example.UltiOauth.DTO.NoteDTO;
import com.example.UltiOauth.Service.NoteService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("jwt/note")
public class NoteController {

    private final NoteService noteService;


    public NoteController(NoteService noteService) {
        this.noteService = noteService;
    }

    @GetMapping
    public List<NoteDTO> findAll() {
        return noteService.findAll();
    }
}
