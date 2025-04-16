package com.example.afet.radar.service;

import com.example.afet.radar.dto.EarthquakeEventNotificationDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class WebSocketService {

    private final SimpMessagingTemplate simpMessagingTemplate;


    public void sendEmergencyNotification (EarthquakeEventNotificationDto earthquakeEventNotificationDtoList){
        log.info("Sending emergency notification: {}", earthquakeEventNotificationDtoList);
        simpMessagingTemplate.convertAndSend("/topic/earthquake", earthquakeEventNotificationDtoList);
    }

}


