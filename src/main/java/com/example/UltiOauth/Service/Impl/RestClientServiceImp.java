package com.example.UltiOauth.Service.Impl;

import com.example.UltiOauth.DTO.WebSocketAnnouncementDTO;
import com.example.UltiOauth.Event.SetAdminEvent;
import com.example.UltiOauth.Service.RestClientService;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.EventListener;
import org.springframework.http.*;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Objects;

@Service
@Slf4j
public class RestClientServiceImp implements RestClientService {

    @Value("${app.websocketServer}")
    private String websocketServerURL;

    private static RestTemplate getRestTemplate(){
        // Use SimpleClientHttpRequestFactory for basic configuration
        SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
        // Set the connection timeout (in milliseconds)
        requestFactory.setConnectTimeout(5 * 1000); // 5 seconds
        return new RestTemplate(requestFactory);
    }

    private static<T> HttpEntity<T> getRequestEntity(T requestBody){
        // Create headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return new HttpEntity<T>(requestBody, headers);
    }

    @Override
    @EventListener(classes = SetAdminEvent.class)
    public void notifySetAdmin(SetAdminEvent setAdminEvent) {
        try {
            RestTemplate restTemplate = getRestTemplate();
            HttpEntity<WebSocketAnnouncementDTO> requestEntity = getRequestEntity(setAdminEvent.getWebSocketAnnouncementDTO());
            // Make the HTTP POST request
            ResponseEntity<Boolean> responseEntity = restTemplate.postForEntity(websocketServerURL + "/api/mess/announce", requestEntity, Boolean.class);
            if (Objects.equals(responseEntity.getBody(), true)){
                log.info("Send message successfully");
            }
            else{
                log.error("Send message fails with status code " + responseEntity.getStatusCode());
            }
        } catch (HttpClientErrorException exception) {
            log.error("HTTPS CLIENT ERROR WHEN NOTIFY SET ADMIN");
            throw exception;
        }

    }
}
