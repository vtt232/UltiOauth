package com.example.UltiOauth.Service;

import com.example.UltiOauth.DTO.WebSocketAnnouncementDTO;
import com.example.UltiOauth.Event.SetAdminEvent;
import org.springframework.kafka.core.KafkaTemplate;

public interface RestClientService {
    //void notifySetAdmin(SetAdminEvent setAdminEvent);
    void notifySetAdmin(SetAdminEvent setAdminEvent);
}
