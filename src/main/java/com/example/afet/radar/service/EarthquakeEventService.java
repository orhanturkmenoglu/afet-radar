package com.example.afet.radar.service;

import com.example.afet.radar.client.AfadApiClient;
import com.example.afet.radar.dto.EarthquakeEventDto;
import com.example.afet.radar.mapper.EarthquakeEventMapper;
import com.example.afet.radar.model.EarthquakeEvent;
import com.example.afet.radar.repository.EarthquakeEventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EarthquakeEventService {

    private final AfadApiClient afadApiClient;

    private final EarthquakeEventMapper earthquakeEventMapper;

    private final EarthquakeEventRepository earthquakeEventRepository;

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


    @Scheduled(fixedRate = 300000)
    @Transactional
    public void updateEarthquakeEvents() {
        LocalDateTime start = LocalDateTime.now().minusDays(1);
        LocalDateTime end = LocalDateTime.now();

        fetchAndSaveNewEvents(start, end);
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
