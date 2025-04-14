package com.example.afet.radar.service;

import com.example.afet.radar.client.AfadApiClient;
import com.example.afet.radar.model.EarthquakeEvent;
import com.example.afet.radar.repository.EarthquakeEventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EarthquakeEventService {

    private final AfadApiClient afadApiClient;
    private final EarthquakeEventRepository earthquakeEventRepository;


    // AFAD API'den veri çekme ve kaydetme
    // AFAD API'den veri çekip kaydetme
    public void fetchAndSaveEarthquakeEvents(LocalDateTime startDate, LocalDateTime endDate) {

        // AFAD API'sinden verileri çekme
        List<EarthquakeEvent> earthquakeEvents = afadApiClient.fetchEarthquakeEvents(startDate, endDate);

        // Veritabanına kaydetme
        earthquakeEventRepository.saveAll(earthquakeEvents);
    }



}
