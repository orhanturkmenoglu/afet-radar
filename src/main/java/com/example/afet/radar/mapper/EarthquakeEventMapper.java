package com.example.afet.radar.mapper;

import com.example.afet.radar.dto.EarthquakeEventDto;
import com.example.afet.radar.dto.EarthquakeEventNotificationDto;
import com.example.afet.radar.model.EarthquakeEvent;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class EarthquakeEventMapper {

    public EarthquakeEventDto toDto(EarthquakeEvent earthquakeEvent) {
        return EarthquakeEventDto.builder()
                .rms(earthquakeEvent.getRms())
                .eventID(earthquakeEvent.getEventID())
                .location(earthquakeEvent.getLocation())
                .latitude(earthquakeEvent.getLatitude())
                .longitude(earthquakeEvent.getLongitude())
                .depth(earthquakeEvent.getDepth())
                .type(earthquakeEvent.getType())
                .magnitude(earthquakeEvent.getMagnitude())
                .country(earthquakeEvent.getCountry())
                .province(earthquakeEvent.getProvince())
                .district(earthquakeEvent.getDistrict())
                .neighborhood(earthquakeEvent.getNeighborhood())
                .date(earthquakeEvent.getDate())
                .isEventUpdate(earthquakeEvent.getIsEventUpdate())
                .lastUpdateDate(earthquakeEvent.getLastUpdateDate())
                .build();
    }

    public EarthquakeEventNotificationDto toNotificationDto(EarthquakeEvent earthquakeEvent) {
        String message = "⚠️ Acil: " + earthquakeEvent.getLocation() +
                " bölgesinde " + earthquakeEvent.getMagnitude() +
                " büyüklüğünde deprem meydana geldi.";

        return EarthquakeEventNotificationDto.builder()
                .location(earthquakeEvent.getLocation())
                .depth(earthquakeEvent.getDepth())
                .magnitude(earthquakeEvent.getMagnitude())
                .country(earthquakeEvent.getCountry())
                .province(earthquakeEvent.getProvince())
                .district(earthquakeEvent.getDistrict())
                .date(earthquakeEvent.getDate())
                .lastUpdateDate(earthquakeEvent.getLastUpdateDate())
                .message(message)
                .build();
    }

    public List<EarthquakeEventDto> toDto(List<EarthquakeEvent> earthquakeEvents) {
        return earthquakeEvents.stream().map(this::toDto).toList();
    }
}
