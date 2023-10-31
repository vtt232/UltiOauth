package com.example.UltiOauth.Service.Impl;

import com.example.UltiOauth.DTO.NoteDTO;
import com.example.UltiOauth.Entity.NoteEntity;
import com.example.UltiOauth.Entity.RepoEntity;
import com.example.UltiOauth.Mapper.NoteMapper;
import com.example.UltiOauth.Repository.NoteRepository;
import com.example.UltiOauth.Repository.RepoRepository;
import com.example.UltiOauth.Service.NoteService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class NoteServiceImp implements NoteService {

    private final NoteRepository noteRepository;
    private final RepoRepository repoRepository;

    public NoteServiceImp(NoteRepository noteRepository, RepoRepository repoRepository) {
        this.noteRepository = noteRepository;
        this.repoRepository = repoRepository;
    }


    public List<NoteDTO> getAllNotesByRepoIdAndUsername(String username, long repoId) {;

        Optional<List<NoteEntity>> notes = noteRepository.findNotesByRepoIdAndUsername(username, repoId);
        List<NoteDTO> noteDTOS = new ArrayList<>();

        if(notes.isPresent()){
            for(NoteEntity note: notes.get()){
                noteDTOS.add(NoteMapper.fromEntityToDto(note));
            }
        }
        return noteDTOS;

    }


    public long findDataSize(){
        return noteRepository.count();
    }

    @Override
    public List<NoteDTO> addNote(NoteDTO newNote, String username, long repoId) {
        NoteEntity newNoteEntity = NoteMapper.fromDtoToEntity(newNote,new NoteEntity());
        RepoEntity repoEntity = repoRepository.findReposByUsernameAndRepoId(username, repoId);
        newNoteEntity.setRepo(repoEntity);
        noteRepository.save(newNoteEntity);
        return getAllNotesByRepoIdAndUsername(username, repoId);
    }

    @Override
    public List<NoteDTO> updateNote(NoteDTO newNote, String username, long repoId, long noteId) {
        NoteEntity updateNoteEntity = NoteMapper.fromDtoToEntity(newNote,new NoteEntity());
        RepoEntity repoEntity = repoRepository.findReposByUsernameAndRepoId(username, repoId);
        updateNoteEntity.setRepo(repoEntity);
        updateNoteEntity.setId(noteId);
        noteRepository.save(updateNoteEntity);
        return getAllNotesByRepoIdAndUsername(username, repoId);
    }

    @Override
    public List<NoteDTO> deleteNote(String username, long repoId, long noteId) {
        Optional<NoteEntity> deleteNoteEntity = noteRepository.findNotesByNoteIdAndRepoIdAndUsername(username, repoId, noteId);
        System.out.println(deleteNoteEntity.get().toString());
        if(deleteNoteEntity.isPresent()){
            noteRepository.delete(deleteNoteEntity.get());
        }
        return getAllNotesByRepoIdAndUsername(username, repoId);
    }
}
