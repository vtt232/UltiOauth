package com.example.UltiOauth.Repository;

import com.example.UltiOauth.Entity.RepoEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RepoRepository extends JpaRepository<RepoEntity, Integer> {

    @Query("SELECT r FROM RepoEntity r WHERE r.owner.username = :ownerName")
    public Page<RepoEntity> findReposByUsername(@Param("ownerName") String ownerName, Pageable pageable);

    @Query("SELECT r FROM RepoEntity r WHERE r.owner.username = :ownerName AND r.id = :repoId")
    public RepoEntity findReposByUsernameAndRepoId(@Param("ownerName") String ownerName, @Param("repoId") long repoId);
}
