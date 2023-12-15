package com.example.UltiOauth.Event;

import com.example.UltiOauth.DTO.WebSocketAnnouncementDTO;
import org.springframework.context.ApplicationEvent;

public class UpdateSystemStatisticsEvent extends ApplicationEvent {

    private WebSocketAnnouncementDTO webSocketAnnouncementDTO;


    public UpdateSystemStatisticsEvent(Object source, WebSocketAnnouncementDTO webSocketAnnouncementDTO) {
        super(source);
        this.webSocketAnnouncementDTO = webSocketAnnouncementDTO;
    }

    public WebSocketAnnouncementDTO getWebSocketAnnouncementDTO() {
        return this.webSocketAnnouncementDTO;
    }
}
