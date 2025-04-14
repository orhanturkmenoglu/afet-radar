package com.example.afet.radar.mapper;

import com.example.afet.radar.dto.EarthquakeEventDto;
import com.example.afet.radar.model.EarthquakeEvent;
import org.springframework.stereotype.Component;

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
}
