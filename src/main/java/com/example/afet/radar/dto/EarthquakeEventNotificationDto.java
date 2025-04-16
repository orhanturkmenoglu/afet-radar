package com.example.afet.radar.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EarthquakeEventNotificationDto {
    private String location;
    private Double depth;
    private Double magnitude;
    private String country;
    private String province;
    private String district;
    private LocalDateTime date;
    private LocalDateTime lastUpdateDate;
    private String message;
}
