package com.example.UltiOauth.Service;

import com.example.UltiOauth.DTO.WebSocketAnnouncementDTO;
import com.example.UltiOauth.Event.SetAdminEvent;
import com.example.UltiOauth.Event.UpdateSystemStatisticsEvent;
import org.springframework.kafka.core.KafkaTemplate;

public interface RestClientService {
    //void notifySetAdmin(SetAdminEvent setAdminEvent);
    void notifySetAdmin(SetAdminEvent setAdminEvent);

    void notifyUpdatingSystemStatistics(UpdateSystemStatisticsEvent updateSystemStatisticsEvent);
}
