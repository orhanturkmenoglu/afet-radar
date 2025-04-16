package com.example.afet.radar.service;

import com.example.afet.radar.client.AfadApiClient;
import com.example.afet.radar.dto.EarthquakeEventDto;
import com.example.afet.radar.dto.EarthquakeEventNotificationDto;
import com.example.afet.radar.mapper.EarthquakeEventMapper;
import com.example.afet.radar.model.EarthquakeEvent;
import com.example.afet.radar.repository.EarthquakeEventRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class EarthquakeEventService {

    private final AfadApiClient afadApiClient;

    private final EarthquakeEventMapper earthquakeEventMapper;

    private final EarthquakeEventRepository earthquakeEventRepository;

    private final WebSocketService webSocketService;

    @Transactional
    public List<EarthquakeEventDto> getEarthquakeEvents(LocalDateTime start, LocalDateTime end) {

        if (Optional.ofNullable(start).isEmpty() || java.util.Optional.ofNullable(end).isEmpty()) {
            start = LocalDateTime.now().minusDays(1);
            end = LocalDateTime.now();
        }

        List<EarthquakeEvent> earthquakeEvents = fetchAndSaveNewEvents(start, end);

        return earthquakeEvents.stream()
                .map(earthquakeEventMapper::toDto)
                .collect(Collectors.toList());
    }


    @Scheduled(fixedRate = 60000)
    @Transactional
    public void updateEarthquakeEvents() {
        LocalDateTime start = LocalDateTime.now().minusDays(1);
        LocalDateTime end = LocalDateTime.now();

        fetchAndSaveNewEvents(start, end);
    }

    public List<EarthquakeEventDto> getByMagnitude(Double magnitude) {
        // Büyüklüğü belirtilen değerin üstündeki tüm depremleri alıyoruz.
        List<EarthquakeEvent> earthquakeEvents = earthquakeEventRepository.findByMagnitudeGreaterThan(magnitude);

        if (earthquakeEvents.isEmpty()) {
            throw new IllegalArgumentException("No earthquake events found with magnitude greater than " + magnitude);
        }

        // Acil durum uyarısını WebSocket ile tüm kullanıcıları bilgilendirerek gönderiyoruz
        // Her bir deprem eventi için uyarı gönderilir.
        for (EarthquakeEvent earthquakeEvent : earthquakeEvents) {
            EarthquakeEventNotificationDto notificationDto = earthquakeEventMapper.toNotificationDto(earthquakeEvent);
            webSocketService.sendEmergencyNotification(notificationDto);
        }

        // EarthquakeEvent'leri DTO'lara dönüştürüp geri döndürüyoruz.
        return earthquakeEventMapper.toDto(earthquakeEvents);
    }


    private List<EarthquakeEvent> fetchAndSaveNewEvents(LocalDateTime start, LocalDateTime end) {
        List<EarthquakeEvent> earthquakeEvents = afadApiClient.fetchEarthquakeEvents(start, end);
        List<String> existingEventIds = earthquakeEventRepository.findAllEventIds();

        List<EarthquakeEvent> newEarthquakeEvents = earthquakeEvents.stream()
                .filter(event -> !existingEventIds.contains(event.getEventID()))
                .toList();

        if (!newEarthquakeEvents.isEmpty()) {
            earthquakeEventRepository.saveAll(newEarthquakeEvents);
        }

        return earthquakeEvents;
    }
}
