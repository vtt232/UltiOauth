package com.example.UltiOauth.Serializer;

import com.example.UltiOauth.DTO.WebSocketAnnouncementDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.Serializer;

@Slf4j
public class WebsocketAnnouncementSerializer implements Serializer<WebSocketAnnouncementDTO> {

    private final ObjectMapper objectMapper = new ObjectMapper();
    @Override
    public byte[] serialize(String topic, WebSocketAnnouncementDTO data) {
        try {
            if (data == null){
                log.info("Null received at serializing");
                return null;
            }
            log.info("Serializing...");
            return objectMapper.writeValueAsBytes(data);
        } catch (Exception e) {
            throw new SerializationException("Error when serializing WebSocketAnnouncementDTO to byte[]");
        }
    }
}
