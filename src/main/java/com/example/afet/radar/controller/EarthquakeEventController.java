package com.example.afet.radar.controller;

import com.example.afet.radar.dto.EarthquakeEventDto;
import com.example.afet.radar.service.EarthquakeEventService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/earthquake")
@RequiredArgsConstructor
public class EarthquakeEventController {

    private final EarthquakeEventService earthquakeService;

    @Operation(summary = "Deprem Olaylarını Getir", description = "Belirtilen tarih aralığında deprem olaylarını getirir.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Deprem olayları başarıyla getirildi."),
            @ApiResponse(responseCode = "400", description = "Geçersiz tarih aralığı girildi.")
    })
    @GetMapping("/get-events")
    public List<EarthquakeEventDto> getEarthquakeEvents(
            @RequestParam(required = false) @Parameter(description = "Deprem olaylarını filtrelemek için başlangıç tarihi") LocalDateTime startDate,
            @RequestParam(required = false) @Parameter(description = "Deprem olaylarını filtrelemek için bitiş tarihi") LocalDateTime endDate) {
        return earthquakeService.getEarthquakeEvents(startDate, endDate);
    }

    @Operation(summary = "Deprem Olaylarını Güncelle", description = "Deprem olaylarının güncellenmesini tetikler.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Deprem olayları başarıyla güncellendi."),
            @ApiResponse(responseCode = "500", description = "Deprem olaylarını güncellerken iç hata oluştu.")
    })
    @PostMapping("/update")
    public ResponseEntity<String> updateEarthquakeEvents() {
        earthquakeService.updateEarthquakeEvents();
        return new ResponseEntity<>("Deprem olayları başarıyla güncellendi.", HttpStatus.OK);
    }

    @Operation(summary = "İle Deprem Olaylarını Getir", description = "Belirli bir ildeki deprem olaylarını getirir.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "İl için deprem olayları başarıyla getirildi."),
            @ApiResponse(responseCode = "400", description = "Geçersiz il adı girildi.")
    })
    @GetMapping("/province")
    public ResponseEntity<List<EarthquakeEventDto>> getEarthquakeEventByProvince(
            @RequestParam(required = true) @Parameter(description = "Deprem olaylarını filtrelemek için il adı") String province) {
        List<EarthquakeEventDto> earthquakeEventDtoList = earthquakeService.getEarthquakeEventByProvince(province);
        return new ResponseEntity<>(earthquakeEventDtoList, HttpStatus.OK);
    }

    @Operation(summary = "Büyüklüğe Göre Deprem Olaylarını Getir", description = "Belirtilen büyüklükten büyük deprem olaylarını getirir.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Büyüklüğü belirtilen değerden büyük deprem olayları başarıyla getirildi."),
            @ApiResponse(responseCode = "400", description = "Geçersiz büyüklük değeri girildi.")
    })
    @GetMapping("/filter-by-magnitude")
    @SendTo("/topic/earthquake")
    public ResponseEntity<List<EarthquakeEventDto>> getByMagnitudeGreaterThan(
            @RequestParam(required = false, defaultValue = "5.0") @Parameter(description = "Deprem olaylarını filtrelemek için minimum büyüklük değeri") Double magnitude) {
        List<EarthquakeEventDto> response = earthquakeService.getByMagnitude(magnitude);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
