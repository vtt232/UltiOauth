package com.example.UltiOauth.DTO;

import com.example.UltiOauth.Entity.ServerEvent;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class WebSocketAnnouncementDTO {
    ServerEvent serverEvent;
    String receiver;
}
