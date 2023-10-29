package com.example.UltiOauth.Repository;

import com.example.UltiOauth.Entity.NoteEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NoteRepository extends JpaRepository<NoteEntity, Long> {
}
