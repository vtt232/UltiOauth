package com.example.UltiOauth.DTO;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class RepoDTO {
    private long id;
    private String name;
    private String url;
    private String language;
}
