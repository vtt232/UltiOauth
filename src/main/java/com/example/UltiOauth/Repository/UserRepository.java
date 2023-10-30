package com.example.UltiOauth.Repository;

import com.example.UltiOauth.Entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

    Optional<UserEntity> findByLink(String link);

    Optional<UserEntity> findByUsername(String username);
}
