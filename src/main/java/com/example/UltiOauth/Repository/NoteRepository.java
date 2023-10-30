package com.example.UltiOauth.Repository;

import com.example.UltiOauth.Entity.NoteEntity;
import com.example.UltiOauth.Entity.RepoEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface NoteRepository extends JpaRepository<NoteEntity, Long> {

    @Query("SELECT n FROM NoteEntity n WHERE n.repo.repo_name = :repoName AND n.repo.owner.username = :ownerName")
    public Page<NoteEntity> findNotesByRepoNameAndUsername(@Param("ownerName") String ownerName, @Param("repoName") String repoName, Pageable pageable);
}
