package com.example.UltiOauth.Controller;

import com.example.UltiOauth.DTO.RepoDTO;
import com.example.UltiOauth.Entity.RepoEntity;
import com.example.UltiOauth.Service.RepoService;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient;
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
@RequestMapping("jwt/repo")
public class RepoController {

    private final WebClient webClient;


    private final RepoService repoService;


    public RepoController(WebClient webClient, RepoService repoService) {
        this.webClient = webClient;
        this.repoService = repoService;
    }

    @GetMapping(path ="/pull", produces = "application/json")
    public ResponseEntity<List<RepoDTO>> pullUserRepoInfor(@RegisteredOAuth2AuthorizedClient OAuth2AuthorizedClient client, @AuthenticationPrincipal OAuth2User oAuth2User){

        List<RepoDTO>  response = webClient.get().uri("https://api.github.com/users/{username}/repos", oAuth2User.getAttributes().get("login")) .attributes(oauth2AuthorizedClient(client))
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<RepoDTO>>() {}).block();
        List<RepoDTO> repoDTOs = new ArrayList<>();
        String username = oAuth2User.getAttribute("login");
        for(RepoDTO repoDTO : response){
            repoDTOs.add(repoService.createRepo(repoDTO, username));
        }
        return ResponseEntity.ok().body(repoDTOs);
    }

    @GetMapping(path = "",produces = "application/json")
    public ResponseEntity<List<RepoDTO>> getAllRepos(@RequestParam(name="page", defaultValue = "0") int pageNo,
                                                     @RequestParam(name="size", defaultValue = "4") int pageSize,
                                                     @RequestParam(name="sort",defaultValue = "id") String sortBy,
                                                     @AuthenticationPrincipal OAuth2User oAuth2User, Principal principal)
    {
        String username = oAuth2User.getAttribute("login");
        List<RepoDTO> repoDTOs = repoService.getAllRepoByUsername(pageNo, pageSize, sortBy, username);
        return ResponseEntity.ok().body(repoDTOs);
    }






}
