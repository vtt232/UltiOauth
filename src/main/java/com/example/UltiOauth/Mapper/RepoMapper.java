package com.example.UltiOauth.Mapper;

import com.example.UltiOauth.DTO.RepoDTO;
import com.example.UltiOauth.DTO.UserDTO;
import com.example.UltiOauth.Entity.RepoEntity;

public class RepoMapper {
    public static RepoEntity fromDtoToRepo(RepoDTO repoDTO, RepoEntity repoEntity){
        return repoEntity.toBuilder().repo_name(repoDTO.getName()).repo_url(repoDTO.getUrl()).language(repoDTO.getLanguage()).build();
    }

    public static  RepoDTO fromRepoToDto(RepoEntity repoEntity){
        return new RepoDTO(repoEntity.getId(),repoEntity.getRepo_name(), repoEntity.getRepo_url(), repoEntity.getLanguage());
    }
}
