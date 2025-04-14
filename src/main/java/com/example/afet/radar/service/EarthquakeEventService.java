package com.example.afet.radar.service;

import com.example.afet.radar.client.AfadApiClient;
import com.example.afet.radar.dto.EarthquakeEventDto;
import com.example.afet.radar.mapper.EarthquakeEventMapper;
import com.example.afet.radar.model.EarthquakeEvent;
import com.example.afet.radar.projection.EarthquakeEventView;
import com.example.afet.radar.repository.EarthquakeEventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EarthquakeEventService {

    private final AfadApiClient afadApiClient;

    private final EarthquakeEventMapper earthquakeEventMapper;

    private final EarthquakeEventRepository earthquakeEventRepository;

    @Transactional
    public List<EarthquakeEventDto> getEarthquakeEvents(LocalDateTime start, LocalDateTime end) {

        if (start == null || end == null) {
            start = LocalDateTime.now().minusDays(1);
            end = LocalDateTime.now();
        }

        List<EarthquakeEvent> earthquakeEvents = afadApiClient.fetchEarthquakeEvents(start, end);

        List<String> existingEventIds = earthquakeEventRepository.findAllEventIds();

        if (!earthquakeEvents.isEmpty()){
            List<EarthquakeEvent> newEarthquakeEvents = earthquakeEvents.stream()
                    .filter(event -> !existingEventIds.contains(event.getEventID()))
                    .toList();

            earthquakeEventRepository.saveAll(newEarthquakeEvents);
        }

        return earthquakeEvents.stream()
                .map(earthquakeEventMapper::toDto)
                .collect(Collectors.toList());
    }


    @Scheduled(fixedRate = 300000)
    @Transactional
    public void updateEarthquakeEvents() {
        LocalDateTime start = LocalDateTime.now().minusDays(1);
        LocalDateTime end = LocalDateTime.now();

        List<EarthquakeEvent> earthquakeEvents = afadApiClient.fetchEarthquakeEvents(start, end);
        List<String> existingEventIds = earthquakeEventRepository.findAllEventIds();

        if (!earthquakeEvents.isEmpty()){
            List<EarthquakeEvent> newEarthquakeEvents = earthquakeEvents.stream()
                    .filter(event -> !existingEventIds.contains(event.getEventID()))
                    .collect(Collectors.toList());

            earthquakeEventRepository.saveAll(newEarthquakeEvents);
        }
    }
}
