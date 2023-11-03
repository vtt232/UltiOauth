package com.example.UltiOauth.DTO;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class AdminRequestDTO {
    private String usernameOfAdmin;
}
