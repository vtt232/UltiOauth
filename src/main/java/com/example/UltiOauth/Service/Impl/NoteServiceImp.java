package com.example.UltiOauth.Service.Impl;

import com.example.UltiOauth.DTO.NoteDTO;
import com.example.UltiOauth.Entity.NoteEntity;
import com.example.UltiOauth.Mapper.NoteMapper;
import com.example.UltiOauth.Repository.NoteRepository;
import com.example.UltiOauth.Service.NoteService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class NoteServiceImp implements NoteService {

    private final NoteRepository noteRepository;

    public NoteServiceImp(NoteRepository noteRepository) {
        this.noteRepository = noteRepository;
    }


    @Override
    public List<NoteDTO> findAll() {
        List<NoteEntity> noteEntities = noteRepository.findAll();
        List<NoteDTO> noteDTOS = new ArrayList<NoteDTO>();
        for(NoteEntity noteEntity:noteEntities){
            noteDTOS.add(NoteMapper.mapNoteEntityToDTO(noteEntity));
        }
        return noteDTOS;
    }
}
