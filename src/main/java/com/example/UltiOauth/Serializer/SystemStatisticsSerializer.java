package com.example.UltiOauth.Serializer;

import com.example.UltiOauth.DTO.SystemStatisticsDTO;
import com.example.UltiOauth.DTO.WebSocketAnnouncementDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.Serializer;

@Slf4j
public class SystemStatisticsSerializer implements Serializer<SystemStatisticsDTO> {
    private final ObjectMapper objectMapper = new ObjectMapper();
    @Override
    public byte[] serialize(String topic, SystemStatisticsDTO data) {
        try {
            if (data == null){
                log.info("Null received at serializing");
                return null;
            }
            log.info("Serializing...");
            return objectMapper.writeValueAsBytes(data);
        } catch (Exception e) {
            throw new SerializationException("Error when serializing SystemStatisticsDTO to byte[]");
        }
    }
}
