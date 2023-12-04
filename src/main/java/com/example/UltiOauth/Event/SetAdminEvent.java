package com.example.UltiOauth.Event;

import com.example.UltiOauth.DTO.WebSocketAnnouncementDTO;
import org.springframework.context.ApplicationEvent;

public class SetAdminEvent extends ApplicationEvent {
    private WebSocketAnnouncementDTO webSocketAnnouncementDTO;

    public SetAdminEvent(Object source, WebSocketAnnouncementDTO webSocketAnnouncementDTO) {
        super(source);
        this.webSocketAnnouncementDTO = webSocketAnnouncementDTO;
    }
    public WebSocketAnnouncementDTO getWebSocketAnnouncementDTO() {
        return webSocketAnnouncementDTO;
    }
}
