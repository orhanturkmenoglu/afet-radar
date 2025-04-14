package com.example.afet.radar.controller;

import com.example.afet.radar.dto.EarthquakeEventDto;
import com.example.afet.radar.service.EarthquakeEventService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/earthquake")
@RequiredArgsConstructor
public class EarthquakeEventController {

    private final EarthquakeEventService earthquakeService;

    @GetMapping("/get-events")
    public List<EarthquakeEventDto> getEarthquakeEvents(@RequestParam(required = false) LocalDateTime startDate,
                                                        @RequestParam(required = false) LocalDateTime endDate) {
        return earthquakeService.earthquakeEventDto(startDate, endDate);
    }
}