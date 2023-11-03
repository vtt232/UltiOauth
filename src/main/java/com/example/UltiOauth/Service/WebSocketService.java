package com.example.UltiOauth.Service;

import com.example.UltiOauth.DTO.WebSocketAnnouncementDTO;
import com.example.UltiOauth.Entity.ServerEvent;

public interface WebSocketService {
    public void transfer(WebSocketAnnouncementDTO webSocketAnnouncementDTO);
}
