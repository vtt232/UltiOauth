package com.example.UltiOauth.Service.Impl;

import com.example.UltiOauth.Service.WebSocketService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import com.example.UltiOauth.DTO.WebSocketAnnouncementDTO;

@Service
@Slf4j
public class WebSocketServiceImpl implements WebSocketService {


    private SimpMessagingTemplate messagingTemplate;

    public WebSocketServiceImpl(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    public void transfer(WebSocketAnnouncementDTO webSocketAnnouncementDTO) {
        log.info(webSocketAnnouncementDTO.getReceiver());
        messagingTemplate.convertAndSend(
                "/queue/notify",
                webSocketAnnouncementDTO
        );
        log.info("TRANSFERED MESSAGE");
    }

}
