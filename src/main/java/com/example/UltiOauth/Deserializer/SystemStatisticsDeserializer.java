package com.example.UltiOauth.Deserializer;

import com.example.UltiOauth.DTO.SystemStatisticsDTO;
import com.example.UltiOauth.DTO.WebSocketAnnouncementDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.Deserializer;

@Slf4j
public class SystemStatisticsDeserializer implements Deserializer<SystemStatisticsDTO> {
    private ObjectMapper objectMapper = new ObjectMapper();
    @Override
    public SystemStatisticsDTO deserialize(String topic, byte[] data) {

        try {
            if (data == null){
                log.info("Null received at deserializing");
                return null;
            }
            log.info("Deserializing...");
            return objectMapper.readValue(new String(data, "UTF-8"), SystemStatisticsDTO.class);
        } catch (Exception e) {
            throw new SerializationException("Error when deserializing byte[] to MessageDto");
        }
    }

}
