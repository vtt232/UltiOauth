package com.example.UltiOauth.Service;

import com.example.UltiOauth.DTO.NoteDTO;

import java.util.List;
import java.util.Optional;

public interface NoteService {

    public List<NoteDTO> getAllNotesByRepoIdAndUsername(String username, long repoId);
    public long findDataSize();

    public List<NoteDTO> addNote(NoteDTO newNote, String username, long repoId);

    public List<NoteDTO> updateNote(NoteDTO newNote, String username, long repoId, long noteId);

    List<NoteDTO> deleteNote(String username, long repoId, long noteId);
}
