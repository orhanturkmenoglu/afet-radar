package com.example.afet.radar.client;

import com.example.afet.radar.model.EarthquakeEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class AfadApiClient {

    private final RestTemplate restTemplate;

    private final String BASE_URL = "https://deprem.afad.gov.tr/apiv2/event/filter";

    /// AFAD API'sine tarihleri doğru formatta gönderiyoruz
    public List<EarthquakeEvent> fetchEarthquakeEvents(LocalDateTime start, LocalDateTime end) {
        log.info("AFAD API'ye deprem verisi çekmek için istek gönderiliyor. Başlangıç: {}, Bitiş: {}", start, end);

        // URL'yi oluşturuyoruz
        String url = BASE_URL + "?start=" + start + "&end=" + end + "&limit=1000&orderby=desc";
        log.info("AFAD API URL'si: {}", url);

        // API'ye istek gönderme
        EarthquakeEvent[] earthquakeEvents = null;
        try {
            earthquakeEvents = restTemplate.getForObject(url, EarthquakeEvent[].class);
            log.info("AFAD API'den {} deprem verisi alındı", earthquakeEvents.length);
        } catch (Exception e) {
            log.error("AFAD API'den deprem verisi alınırken hata oluştu: {}", e.getMessage());
            throw new RuntimeException("AFAD API'den deprem verisi alınırken hata oluştu", e);
        }

        // Eğer sonuç yoksa
        if (earthquakeEvents.length == 0) {
            log.warn("AFAD API'den alınan deprem verisi boş veya geçersiz.");
        }

        return List.of(earthquakeEvents);
    }
}
