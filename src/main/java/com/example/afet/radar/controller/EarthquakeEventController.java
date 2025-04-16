package com.example.afet.radar.controller;

import com.example.afet.radar.dto.EarthquakeEventDto;
import com.example.afet.radar.service.EarthquakeEventService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
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
        return earthquakeService.getEarthquakeEvents(startDate, endDate);
    }

    @PostMapping("/update")
    public ResponseEntity<String> updateEarthquakeEvents() {
        earthquakeService.updateEarthquakeEvents();
        return new ResponseEntity<>("Earthquake events updated successfully.", HttpStatus.OK);
    }

    @GetMapping("/filter-by-magnitude")
    @SendTo("/topic/earthquake")
    public ResponseEntity<List<EarthquakeEventDto>> getByMagnitudeGreaterThan(@RequestParam(required = false, defaultValue = "5.0") Double magnitude) {
        List<EarthquakeEventDto> response = earthquakeService.getByMagnitude(magnitude);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}