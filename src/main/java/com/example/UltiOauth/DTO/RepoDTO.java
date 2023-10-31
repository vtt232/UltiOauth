package com.example.UltiOauth.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RepoDTO {
    private long id;
    private String name;
    private String url;
    private String language;
}
