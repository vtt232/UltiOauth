package com.example.UltiOauth.Service;

import com.example.UltiOauth.DTO.NoteDTO;

import java.util.List;

public interface NoteService {
    //void save(NoteDTO noteDTO);

    List<NoteDTO> findAll();

    //NoteDTO findById(Long id);

    //void delete(Long id);

}
