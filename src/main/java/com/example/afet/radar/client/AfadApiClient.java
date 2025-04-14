package com.example.afet.radar.client;

import com.example.afet.radar.model.EarthquakeEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

@Component
@RequiredArgsConstructor
public class AfadApiClient {

    private final RestTemplate restTemplate;

    private final String BASE_URL ="https://deprem.afad.gov.tr/apiv2/event/filter";

    /// AFAD API'sine tarihleri doğru formatta gönderiyoruz
    public List<EarthquakeEvent> fetchEarthquakeEvents(LocalDateTime start, LocalDateTime end) {
        // API'ye istek gönderme
        String url = BASE_URL + "?start=" + start+ "&end=" + end;

        // API'den verileri al
        EarthquakeEvent[] earthquakeEvents = restTemplate.getForObject(url, EarthquakeEvent[].class);

        return List.of(earthquakeEvents);
    }


}
