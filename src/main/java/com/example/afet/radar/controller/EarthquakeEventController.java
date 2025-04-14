package com.example.afet.radar.controller;

import com.example.afet.radar.service.EarthquakeEventService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/earthquake")
@RequiredArgsConstructor
public class EarthquakeEventController {

    private final EarthquakeEventService earthquakeService;

    // Afad API'den veri çekip kaydetme
    @PostMapping("/fetch-and-save")
    public String fetchAndSaveEarthquakeEvents(@RequestParam LocalDateTime startDate, @RequestParam LocalDateTime endDate) {
        try {
            earthquakeService.fetchAndSaveEarthquakeEvents(startDate, endDate);
            return "Earthquake events fetched and saved successfully.";
        } catch (Exception e) {
            return "Error occurred: " + e.getMessage();
        }
    }

}