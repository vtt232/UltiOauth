package com.example.UltiOauth.Mapper;

import com.example.UltiOauth.DTO.NoteDTO;
import com.example.UltiOauth.Entity.NoteEntity;

public class NoteMapper {

    public static NoteDTO mapNoteEntityToDTO(NoteEntity noteEntity){
        return new NoteDTO(noteEntity.getId(), noteEntity.getContent());
    }

    public static NoteEntity mapNoteDTOToEntity(NoteDTO noteDTO){
        return new NoteEntity(noteDTO.getId(), noteDTO.getContent());
    }
}
