package com.example.UltiOauth.Repository;

import com.example.UltiOauth.Entity.NoteEntity;
import com.example.UltiOauth.Entity.RepoEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface NoteRepository extends JpaRepository<NoteEntity, Long> {

    @Query("SELECT n FROM NoteEntity n WHERE n.repo.id = :repoId AND n.repo.owner.username = :ownerName")
    public Optional<List<NoteEntity>> findNotesByRepoIdAndUsername(@Param("ownerName") String ownerName, @Param("repoId") long repoId);

    @Query("SELECT n FROM NoteEntity n WHERE n.repo.owner.username = :ownerName AND n.repo.id = :repoId AND n.id = :noteId")
    public Optional<NoteEntity> findNotesByNoteIdAndRepoIdAndUsername(@Param("ownerName") String ownerName, @Param("repoId") long repoId, @Param("noteId") long noteId);
}
