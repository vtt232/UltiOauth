package com.example.UltiOauth.Service.Impl;

import com.example.UltiOauth.DTO.NoteDTO;
import com.example.UltiOauth.DTO.RepoDTO;
import com.example.UltiOauth.Entity.NoteEntity;
import com.example.UltiOauth.Entity.RepoEntity;
import com.example.UltiOauth.Mapper.NoteMapper;
import com.example.UltiOauth.Mapper.RepoMapper;
import com.example.UltiOauth.Repository.NoteRepository;
import com.example.UltiOauth.Service.NoteService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class NoteServiceImp implements NoteService {

    private final NoteRepository noteRepository;

    public NoteServiceImp(NoteRepository noteRepository) {
        this.noteRepository = noteRepository;
    }


    public List<NoteDTO> getAllNotesByRepoNameAndUsername(int pageNo, int pageSize, String sortBy, String username, String repoName) {
        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
        List<NoteEntity> notes = new ArrayList<NoteEntity>();

        Page<NoteEntity> notesPage = noteRepository.findNotesByRepoNameAndUsername(username, repoName, paging);
        notes = notesPage.getContent();

        List<NoteDTO> noteDTOS = new ArrayList<>();
        for(NoteEntity note: notes){
            noteDTOS.add(NoteMapper.fromEntityToDto(note));
        }
        return noteDTOS;

    }


    public long findDataSize(){
        return noteRepository.count();
    }

    @Override
    public List<NoteDTO> addNote(NoteDTO newNote, String username, String repoName) {
        NoteEntity newNoteEntity = NoteMapper.fromDtoToEntity(newNote,new NoteEntity());
        noteRepository.save(newNoteEntity);
        return getAllNotesByRepoNameAndUsername(0,2,"id", username, repoName);
    }
}
