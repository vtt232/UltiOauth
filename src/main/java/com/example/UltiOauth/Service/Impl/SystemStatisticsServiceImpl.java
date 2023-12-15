package com.example.UltiOauth.Service.Impl;

import com.example.UltiOauth.DTO.SystemStatisticsDTO;
import com.example.UltiOauth.Repository.NoteRepository;
import com.example.UltiOauth.Repository.RepoRepository;
import com.example.UltiOauth.Repository.UserRepository;
import com.example.UltiOauth.Service.SystemStatisticsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@Slf4j
public class SystemStatisticsServiceImpl implements SystemStatisticsService {

    private final UserRepository userRepository;
    private final RepoRepository repoRepository;
    private final NoteRepository noteRepository;

    public SystemStatisticsServiceImpl(UserRepository userRepository, RepoRepository repoRepository, NoteRepository noteRepository) {
        this.userRepository = userRepository;
        this.repoRepository = repoRepository;
        this.noteRepository = noteRepository;
    }

    @Override
    public SystemStatisticsDTO updateAndGet() {

        log.info("UPDATE AND GET SYSTEM STATISTICS");

        SystemStatisticsDTO systemStatistics = new SystemStatisticsDTO();

        try {

            systemStatistics.setUserCount(userRepository.count());
            systemStatistics.setRepoCount(repoRepository.count());
            systemStatistics.setNoteCount(noteRepository.count());
            systemStatistics.setLastUpdate(LocalDate.now());

        }
        catch(RuntimeException e) {
            log.error("ERROR WHEN GET SYSTEM STATISTICS");
            throw e;
        }

        return systemStatistics;
    }
}
