package com.example.UltiOauth.Controller;

import com.example.UltiOauth.Annotation.RoutingWith;
import com.example.UltiOauth.DTO.RepoDTO;
import com.example.UltiOauth.Entity.RepoEntity;
import com.example.UltiOauth.Service.RepoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.security.oauth2.client.web.reactive.function.client.ServerOAuth2AuthorizedClientExchangeFilterFunction.oauth2AuthorizedClient;

@RestController
@RequestMapping("api/jwt/repo")
@Slf4j
public class RepoController {

    private final WebClient webClient;


    private final RepoService repoService;


    public RepoController(WebClient webClient, RepoService repoService) {
        this.webClient = webClient;
        this.repoService = repoService;
    }

    @GetMapping(path ="/pull", produces = "application/json")
    @RoutingWith("${app.masterdb}")
    public ResponseEntity<List<RepoDTO>> pullUserRepoInfor(@RegisteredOAuth2AuthorizedClient OAuth2AuthorizedClient client, @AuthenticationPrincipal OAuth2User oAuth2User){

        if(oAuth2User == null){
            log.warn("USER IS NOT AUTHENTICATED");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
        log.info("GET GITHUB REPOSITORIES DATA");
        List<RepoDTO>  response = webClient.get().uri("https://api.github.com/users/{username}/repos", oAuth2User.getAttributes().get("login")) .attributes(oauth2AuthorizedClient(client))
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<RepoDTO>>() {}).block();
        List<RepoDTO> repoDTOs = new ArrayList<>();
        String username = oAuth2User.getAttribute("login");
        log.info("SAVE GITHUB REPOSITORIES DATA TO DATABASE");
        for(RepoDTO repoDTO : response){
            repoDTOs.add(repoService.createRepo(repoDTO, username));
        }
        log.info("PULL DATA SUCCESS");
        return ResponseEntity.ok().body(repoDTOs);
    }

    @GetMapping(path = "",produces = "application/json")
    @RoutingWith("${app.slavedb}")
    public ResponseEntity<List<RepoDTO>> getAllRepos(@AuthenticationPrincipal OAuth2User oAuth2User,
                                                     @RequestParam(name="page", defaultValue = "0") int pageNo,
                                                     @RequestParam(name="size", defaultValue = "4") int pageSize,
                                                     @RequestParam(name="sort",defaultValue = "id") String sortBy)
    {
        if(oAuth2User == null){
            log.warn("USER IS NOT AUTHENTICATED");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
        String username = oAuth2User.getAttribute("login");
        log.info("GET REPOSITORIES DATA FROM DATABASE");
        List<RepoDTO> repoDTOs = repoService.getAllRepoByUsername(pageNo, pageSize, sortBy, username);
        log.info("GET REPOSITORIES DATA SUCCESSFULLY");
        return ResponseEntity.ok().body(repoDTOs);
    }

}
