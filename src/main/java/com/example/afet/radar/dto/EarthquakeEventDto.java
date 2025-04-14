package com.example.afet.radar.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EarthquakeEventDto {
    private String rms;
    private String eventID;
    private String location;
    private String latitude;
    private String longitude;
    private Double depth;
    private String type;
    private Double magnitude;
    private String country;
    private String province;
    private String district;
    private String neighborhood;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime date;
    private boolean isEventUpdate;
    private LocalDateTime lastUpdateDate;
}
