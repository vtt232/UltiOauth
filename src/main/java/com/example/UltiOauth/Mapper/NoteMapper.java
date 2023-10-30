package com.example.UltiOauth.Mapper;

import com.example.UltiOauth.DTO.NoteDTO;
import com.example.UltiOauth.DTO.RepoDTO;
import com.example.UltiOauth.DTO.UserDTO;
import com.example.UltiOauth.Entity.NoteEntity;
import com.example.UltiOauth.Entity.RepoEntity;

public class NoteMapper {

    public static NoteEntity fromDtoToEntity(NoteDTO noteDTO, NoteEntity noteEntity){
        return noteEntity.toBuilder().id(noteDTO.getId()).content(noteDTO.getContent()).build();
    }

    public static  NoteDTO fromEntityToDto(NoteEntity noteEntity){
        return new NoteDTO(noteEntity.getId(), noteEntity.getContent());
    }

}
