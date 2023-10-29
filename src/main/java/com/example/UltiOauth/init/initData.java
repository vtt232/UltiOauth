package com.example.UltiOauth.init;

import com.example.UltiOauth.Entity.NoteEntity;
import com.example.UltiOauth.Repository.NoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class initData implements ApplicationRunner {

    private NoteRepository noteRepository;

    @Autowired
    public initData(NoteRepository noteRepository) {
        this.noteRepository = noteRepository;
    }


    @Override
    public void run(ApplicationArguments args) throws Exception {
        noteRepository.save(new NoteEntity(1L, "Started project"));
        noteRepository.save(new NoteEntity(2L, "Implemented 30%"));
        noteRepository.save(new NoteEntity(3L, "Implemented 40"));
        noteRepository.save(new NoteEntity(4L, "Implemented 50%"));
        noteRepository.save(new NoteEntity(5L, "Implemented 60%"));
        noteRepository.save(new NoteEntity(6L, "Implemented 70"));
        noteRepository.save(new NoteEntity(7L, "Implemented 80%"));
        noteRepository.save(new NoteEntity(8L, "Implemented 90%"));
        noteRepository.save(new NoteEntity(9L, "Implemented 100%"));
        noteRepository.save(new NoteEntity(10L, "Completed all"));

    }
}
