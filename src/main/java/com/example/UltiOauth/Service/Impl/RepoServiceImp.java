package com.example.UltiOauth.Service.Impl;

import com.example.UltiOauth.DTO.RepoDTO;
import com.example.UltiOauth.Entity.RepoEntity;
import com.example.UltiOauth.Entity.UserEntity;
import com.example.UltiOauth.Mapper.RepoMapper;
import com.example.UltiOauth.Repository.RepoRepository;
import com.example.UltiOauth.Repository.UserRepository;
import com.example.UltiOauth.Service.RepoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class RepoServiceImp implements RepoService {
    private final RepoRepository repoRepository;

    private final UserRepository userRepository;


    public RepoServiceImp(RepoRepository repoRepository, UserRepository userRepository){
        this.repoRepository = repoRepository;
        this.userRepository = userRepository;
    }

    public RepoDTO createRepo(RepoDTO repoDTO, String username){

        Optional<RepoEntity> existedRepoEntity = repoRepository.findReposByRepoName(repoDTO.getName());

        if(existedRepoEntity.isPresent()){
            return RepoMapper.fromRepoToDto(existedRepoEntity.get());
        }
        else{
            RepoEntity repoEntity = new RepoEntity();
            repoEntity = RepoMapper.fromDtoToRepo(repoDTO, repoEntity);
            Optional<UserEntity> userEntity = userRepository.findByUsername(username);
            if(userEntity.isPresent()){
                repoEntity.setOwner(userEntity.get());
                repoEntity = repoRepository.save(repoEntity);
            }
            return RepoMapper.fromRepoToDto(repoEntity);
        }

    }

    public  List<RepoDTO> getAllRepoByUsername(int pageNo, int pageSize, String sortBy, String username){
        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
        List<RepoEntity> repos = new ArrayList<RepoEntity>();

        Page<RepoEntity> reposPage = repoRepository.findReposByUsername(username, paging);
        repos = reposPage.getContent();
        List<RepoDTO> repoDTOS = new ArrayList<>();
        for(RepoEntity repo: repos){
            repoDTOS.add(RepoMapper.fromRepoToDto(repo));
        }
        return repoDTOS;

    }
}
