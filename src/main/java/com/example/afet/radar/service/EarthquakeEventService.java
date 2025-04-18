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
        log.info("Deprem olayları {} ve {} tarihleri arasında alınıyor", start, end);

        if (Optional.ofNullable(start).isEmpty() || Optional.ofNullable(end).isEmpty()) {
            start = LocalDateTime.now().minusDays(1);
            end = LocalDateTime.now();
            log.info("Başlangıç ve bitiş tarihleri null idi, son 24 saate varsayıldı: {} - {}", start, end);
        }

        List<EarthquakeEvent> earthquakeEvents = fetchAndSaveNewEvents(start, end);
        log.info("Toplam {} deprem olayı alındı", earthquakeEvents.size());

        return earthquakeEvents.stream()
                .map(earthquakeEventMapper::toDto)
                .collect(Collectors.toList());
    }

    public List<EarthquakeEventDto> getEarthquakeEventByProvince(String province) {
        log.info("İl bazında deprem olayları alınıyor: {}", province);

        if (province == null) {
            log.error("İl bilgisi null olamaz");
            throw new NullPointerException("İl bilgisi null olamaz");
        }

        List<EarthquakeEvent> earthquakeEventList = earthquakeEventRepository.findByProvince(province);
        log.info("{} iline ait {} deprem olayı bulundu", province, earthquakeEventList.size());

        return earthquakeEventMapper.toDto(earthquakeEventList);
    }

    @Scheduled(fixedRate = 30000)
    @Transactional
    public void updateEarthquakeEvents() {
        log.info("Planlı görev ile deprem olayları güncelleniyor");

        LocalDateTime start = LocalDateTime.now().minusDays(1);
        LocalDateTime end = LocalDateTime.now();

        fetchAndSaveNewEvents(start, end);
        log.info("Planlı görev deprem olaylarını güncelleme tamamlandı");
    }

    public List<EarthquakeEventDto> getByMagnitude(Double magnitude) {
        log.info("Büyüklüğü {}'den büyük olan deprem olayları alınıyor", magnitude);

        List<EarthquakeEvent> earthquakeEvents = earthquakeEventRepository.findByMagnitudeGreaterThan(magnitude);

        if (earthquakeEvents.isEmpty()) {
            log.error("{} büyüklüğünden büyük deprem olayı bulunamadı", magnitude);
            throw new IllegalArgumentException("{} büyüklüğünden büyük deprem olayı bulunamadı".formatted(magnitude));
        }

        // WebSocket üzerinden acil durum uyarıları gönderiliyor
        for (EarthquakeEvent earthquakeEvent : earthquakeEvents) {
            EarthquakeEventNotificationDto notificationDto = earthquakeEventMapper.toNotificationDto(earthquakeEvent);
            webSocketService.sendEmergencyNotification(notificationDto);
            log.info("Deprem olayı ID: {} için acil durum bildirimi gönderildi", earthquakeEvent.getEventID());
        }

        log.info("Toplam {} deprem olayı alındı ve uyarıları gönderildi", earthquakeEvents.size());

        return earthquakeEventMapper.toDto(earthquakeEvents);
    }

    private List<EarthquakeEvent> fetchAndSaveNewEvents(LocalDateTime start, LocalDateTime end) {
        log.info("API'den {} ve {} tarihleri arasında yeni deprem olayları alınıyor", start, end);

        List<EarthquakeEvent> earthquakeEvents = afadApiClient.fetchEarthquakeEvents(start, end);
        List<String> existingEventIds = earthquakeEventRepository.findAllEventIds();

        List<EarthquakeEvent> newEarthquakeEvents = earthquakeEvents.stream()
                .filter(event -> !existingEventIds.contains(event.getEventID()))
                .collect(Collectors.toList());

        if (!newEarthquakeEvents.isEmpty()) {
            earthquakeEventRepository.saveAll(newEarthquakeEvents);
            log.info("Toplam {} yeni deprem olayı kaydedildi", newEarthquakeEvents.size());
        } else {
            log.info("Kaydedilecek yeni deprem olayı bulunamadı");
        }

        return earthquakeEvents;
    }
}
