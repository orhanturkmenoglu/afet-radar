package com.example.afet.radar.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDateTime;

@Entity
@Table(name = "earthquake_event")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EarthquakeEvent {

    @Id
    @UuidGenerator
    private String id;

    private String eventID;

    private String location;

    private String latitude; // enlem

    private String longitude; // boylam

    private Double depth ; // derinlik

    private String type;

    private Double magnitude; // büyüklük

    private String country;

    private String province;

    private String district;

    private String neighborhood; // komşu

    private String rms;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime date;

    private Boolean isEventUpdate;

    private LocalDateTime lastUpdateDate;
}
