package com.example.UltiOauth.Service;

import com.example.UltiOauth.DTO.NoteDTO;

import java.util.List;
import java.util.Optional;

public interface NoteService {

    public List<NoteDTO> getAllNotesByRepoNameAndUsername(int pageNo, int pageSize, String sortBy, String username, String repoName);
    public long findDataSize();

    public List<NoteDTO> addNote(NoteDTO newNote, String username, String repoName);
}
