package com.example.UltiOauth.DTO;


import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class NoteDTO {
    private long id;
    private String content;
}
