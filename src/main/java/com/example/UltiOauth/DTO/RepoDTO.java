package com.example.UltiOauth.DTO;

import lombok.*;

import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder(toBuilder = true)
public class RepoDTO {
    private long id;
    private String name;
    private String url;
    private String language;


}
