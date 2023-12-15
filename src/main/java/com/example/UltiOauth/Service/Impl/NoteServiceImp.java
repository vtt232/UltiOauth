package com.example.UltiOauth.Service.Impl;

import com.example.UltiOauth.DTO.NoteDTO;
import com.example.UltiOauth.DTO.WebSocketAnnouncementDTO;
import com.example.UltiOauth.Entity.NoteEntity;
import com.example.UltiOauth.Entity.RepoEntity;
import com.example.UltiOauth.Entity.ServerEvent;
import com.example.UltiOauth.Event.SetAdminEvent;
import com.example.UltiOauth.Event.UpdateSystemStatisticsEvent;
import com.example.UltiOauth.Exception.NoteNotFoundException;
import com.example.UltiOauth.Exception.RepoNotFoundException;
import com.example.UltiOauth.Mapper.NoteMapper;
import com.example.UltiOauth.Repository.NoteRepository;
import com.example.UltiOauth.Repository.RepoRepository;
import com.example.UltiOauth.Service.NoteService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class NoteServiceImp implements NoteService {

    private final NoteRepository noteRepository;
    private final RepoRepository repoRepository;

    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    public NoteServiceImp(NoteRepository noteRepository, RepoRepository repoRepository, ApplicationEventPublisher applicationEventPublisher) {
        this.noteRepository = noteRepository;
        this.repoRepository = repoRepository;
        this.applicationEventPublisher = applicationEventPublisher;
    }


    public List<NoteDTO> getAllNotesByRepoIdAndUsername(String username, long repoId) {;
        log.info("STARTING GETTING NOTES FROM DB");
        Optional<List<NoteEntity>> notes = noteRepository.findNotesByRepoIdAndUsername(username, repoId);
        List<NoteDTO> noteDTOS = new ArrayList<>();

        if(notes.isPresent()){
            log.info("THERE ARE NOTES IN DB");
            for(NoteEntity note: notes.get()){
                noteDTOS.add(NoteMapper.fromEntityToDto(note));
            }
        }
        log.info("RETURN ALL NOTES");
        return noteDTOS;

    }


    public long findDataSize(){
        return noteRepository.count();
    }

    @Override
    public List<NoteDTO> addNote(NoteDTO newNote, String username, long repoId) {
        log.info("STARTING ADDING NOTE");
        NoteEntity newNoteEntity = NoteMapper.fromDtoToEntity(newNote,new NoteEntity());
        Optional<RepoEntity> repoEntity = repoRepository.findReposByUsernameAndRepoId(username, repoId);
        if(repoEntity.isPresent()){
            log.info("FOUND REPO OF REQUIRED NOTE");
            newNoteEntity.setRepo(repoEntity.get());
            noteRepository.save(newNoteEntity);
            log.info("ADDING NOTE SUCCESS");

            WebSocketAnnouncementDTO webSocketAnnouncementDTO = new WebSocketAnnouncementDTO(ServerEvent.CHANGE_NUMBER_OF_NOTE, username);
            UpdateSystemStatisticsEvent updateSystemStatisticsEvent = new UpdateSystemStatisticsEvent(this, webSocketAnnouncementDTO);
            applicationEventPublisher.publishEvent(updateSystemStatisticsEvent);


            return getAllNotesByRepoIdAndUsername(username, repoId);
        }
        else {
            throw new RepoNotFoundException(username, repoId);
        }

    }

    @Override
    public List<NoteDTO> updateNote(NoteDTO newNote, String username, long repoId, long noteId) {
        log.info("STARTING UPDATING NOTE");
        Optional<NoteEntity> updateNoteEntity = noteRepository.findNotesByNoteIdAndRepoIdAndUsername(username, repoId, noteId);
        if(updateNoteEntity.isPresent()){
            log.info("FOUND UPDATING NOTE");
            updateNoteEntity.get().setContent(newNote.getContent());
            noteRepository.save(updateNoteEntity.get());
            log.info("UPDATING NOTE SUCCESS");
            return getAllNotesByRepoIdAndUsername(username, repoId);
        }
        else{
            throw new RepoNotFoundException(username, repoId);
        }
    }

    @Override
    public List<NoteDTO> deleteNote(String username, long repoId, long noteId) {
        log.info("STARTING DELETING NOTE");
        Optional<NoteEntity> deleteNoteEntity = noteRepository.findNotesByNoteIdAndRepoIdAndUsername(username, repoId, noteId);
        if(deleteNoteEntity.isPresent()){
            log.info("FOUND DELETING NOTE");
            noteRepository.delete(deleteNoteEntity.get());
            log.info("DELETING NOTE SUCCESS");

            WebSocketAnnouncementDTO webSocketAnnouncementDTO = new WebSocketAnnouncementDTO(ServerEvent.CHANGE_NUMBER_OF_NOTE, username);
            UpdateSystemStatisticsEvent updateSystemStatisticsEvent = new UpdateSystemStatisticsEvent(this, webSocketAnnouncementDTO);
            applicationEventPublisher.publishEvent(updateSystemStatisticsEvent);

            return getAllNotesByRepoIdAndUsername(username, repoId);
        }
        else{
            throw new NoteNotFoundException(username, repoId, noteId);
        }

    }
}
