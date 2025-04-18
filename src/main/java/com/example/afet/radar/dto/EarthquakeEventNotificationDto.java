package com.example.afet.radar.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "Deprem Olayı Bildirimi")
public class EarthquakeEventNotificationDto {

    @Schema(description = "Deprem olayının gerçekleştiği konum (ilçe, mahalle vs.)", example = "İstanbul, Beşiktaş")
    private String location;

    @Schema(description = "Deprem olayının derinliği (km cinsinden)", example = "10.5")
    private Double depth;

    @Schema(description = "Deprem büyüklüğü", example = "5.8")
    private Double magnitude;

    @Schema(description = "Depremin gerçekleştiği ülke", example = "Türkiye")
    private String country;

    @Schema(description = "Depremin gerçekleştiği il", example = "İstanbul")
    private String province;

    @Schema(description = "Depremin gerçekleştiği ilçe", example = "Beşiktaş")
    private String district;

    @Schema(description = "Depremin gerçekleşme tarihi ve saati", example = "2025-04-18T10:30:00")
    private LocalDateTime date;

    @Schema(description = "Son güncelleme tarihi", example = "2025-04-18T10:45:00")
    private LocalDateTime lastUpdateDate;

    @Schema(description = "Deprem hakkında gönderilen mesaj", example = "5.8 büyüklüğünde deprem meydana geldi. İstanbul'da hissedildi.")
    private String message;
}
