package com.example.UltiOauth.Service;

import com.example.UltiOauth.DTO.RepoDTO;
import com.example.UltiOauth.Entity.RepoEntity;
import com.example.UltiOauth.Entity.UserEntity;
import com.example.UltiOauth.Mapper.RepoMapper;
import com.example.UltiOauth.Mapper.UserMapper;
import com.example.UltiOauth.Repository.RepoRepository;
import com.example.UltiOauth.Repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


public interface RepoService {

    RepoDTO createRepo(RepoDTO repoDTO, String username);

    List<RepoDTO> getAllRepoByUsername(int pageNo, int pageSize, String sortBy, String username);
}
