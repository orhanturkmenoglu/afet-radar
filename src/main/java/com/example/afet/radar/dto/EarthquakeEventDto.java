package com.example.afet.radar.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Deprem Olayı Detayları")
public class EarthquakeEventDto {

    @Schema(description = "Deprem olayı RMS değeri", example = "4.5")
    private String rms;

    @Schema(description = "Deprem olayı ID'si", example = "EQ12345")
    private String eventID;

    @Schema(description = "Deprem olayının konumu (ilçe, mahalle vs.)", example = "İstanbul, Beşiktaş")
    private String location;

    @Schema(description = "Deprem olayının enlem değeri", example = "41.0082")
    private String latitude;

    @Schema(description = "Deprem olayının boylam değeri", example = "28.9784")
    private String longitude;

    @Schema(description = "Deprem olayının derinliği (km cinsinden)", example = "10.5")
    private Double depth;

    @Schema(description = "Deprem türü (örneğin: 'Ana Deprem', 'Artçı Deprem')", example = "Ana Deprem")
    private String type;

    @Schema(description = "Deprem büyüklüğü", example = "5.8")
    private Double magnitude;

    @Schema(description = "Depremin gerçekleştiği ülke", example = "Türkiye")
    private String country;

    @Schema(description = "Depremin gerçekleştiği il", example = "İstanbul")
    private String province;

    @Schema(description = "Depremin gerçekleştiği ilçe", example = "Beşiktaş")
    private String district;

    @Schema(description = "Depremin gerçekleştiği mahalle", example = "Levent")
    private String neighborhood;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    @Schema(description = "Depremin gerçekleşme tarihi ve saati", example = "2025-04-18T10:30:00")
    private LocalDateTime date;

    @Schema(description = "Deprem olayının güncellenip güncellenmediği durumu", example = "false")
    private boolean isEventUpdate;

    @Schema(description = "Son güncelleme tarihi", example = "2025-04-18T10:45:00")
    private LocalDateTime lastUpdateDate;
}
